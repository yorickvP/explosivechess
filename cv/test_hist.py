#!/usr/bin/env python
# -*- coding: utf8 -*-

import cv2
import numpy as np
import lines as ln


class Test(object):
	def __init__(self, filename):
		img = cv2.imread(filename)
		self.orig_img = cv2.resize(img, (0,0), fx=0.25, fy=0.25)
		gray = cv2.cvtColor(self.orig_img,cv2.COLOR_BGR2GRAY)

		self.input_edge = cv2.Canny(self.orig_img, 100, 210)
		self.input_lines = cv2.HoughLines(self.input_edge, 1, np.pi / 180, 130)
		self.in_marked_lines = []
		self.in_corners = []
		self.board_M = None
		self.phase = 0
	def draw_input_edge(self):
		img = cv2.cvtColor(self.input_edge, cv2.COLOR_GRAY2BGR)
		for [[rho,theta]] in self.input_lines:
			(x1, y1), (x2, y2) = ln.linetopoints(rho, theta)
			if (rho, theta) in self.in_marked_lines:
				cv2.line(img,(x1,y1),(x2,y2),(0,255,0),2)
			else:
				cv2.line(img,(x1,y1),(x2,y2),(0,0,255),2)
		for (x, y) in self.in_corners:
			cv2.circle(img, (x, y), 5, (255, 0, 0), -1)
		cv2.imshow('dst', img)
	def draw_board(self):
		dst = cv2.warpPerspective(self.orig_img, self.board_M, (700, 700))

		hsvdst = cv2.cvtColor(dst,cv2.COLOR_BGR2HSV)
		step = 600/8
		black_val = cv2.getTrackbarPos('black', 'warp')
		white_val = cv2.getTrackbarPos('white', 'warp')
		yc = cv2.getTrackbarPos('ycutoff', 'warp')
		font = cv2.FONT_HERSHEY_SIMPLEX
		#dst = hsvdst[...,2]
		col = dst
		dst = cv2.cvtColor(dst,cv2.COLOR_BGR2GRAY)
		rv, bl_dst = cv2.threshold(dst, black_val, 255, cv2.THRESH_BINARY)
		rv, wt_dst = cv2.threshold(dst, white_val, 255, cv2.THRESH_BINARY)
		for i in range(8):
			for j in range(8):
				bl_cnt = np.sum(bl_dst[50+step*i-yc:50+step*i+step-yc,50+step*j:50+step*j+step] == 0)
				wt_cnt = np.sum(wt_dst[50+step*i-yc:50+step*i+step-yc,50+step*j:50+step*j+step] == 255)
				col[50+step*i-yc:50+step*i+step-yc,50+step*j:50+step*j+step,2] = np.zeros((step, step))
				s = str(bl_cnt) + "/" + str(wt_cnt)
				if bl_cnt > 500:
					cv2.circle(col, (50+step*j+step/2, 50+step*i+step/2), 25, (0, 0, 0), -1)
				elif wt_cnt >= 75:
					cv2.circle(col, (50+step*j+step/2, 50+step*i+step/2), 25, (255, 255, 255), -1)
				cv2.putText(col, s ,(50+step*j, 50+step*i+step/2), font, 0.5,(255,255,0),2,cv2.LINE_AA)
		# # draw chess grid
		# for i in range(1, 8):
		# 	cv2.line(dst, ((600/8) * i, 0), ((600/8)*i, 600), (255, 0, 0), 2)
		# 	cv2.line(dst, (0, (600/8) * i), (600, (600/8)*i), (255, 0, 0), 2)
		cv2.imshow('warp', col)
	def draw(self):
		self.draw_input_edge()
		if self.board_M is not None:
			self.draw_board()
	def click(self, x, y):
		ll = self.input_lines
		if self.phase is 0:
			sides = [ln.findlineleftof(ll, x, y), ln.findlinetopof(ll, x, y)]
			self.in_corners = [ln.intersect(sides[0], sides[1])]
			self.phase = 1
			self.in_marked_lines = sides
		elif self.phase is 1:
			# lines: left, top, right, bottom
			sides = self.in_marked_lines
			sides += [ln.findlinerightof(ll, x, y), ln.findlinebottomof(ll, x, y)]
			# intersects: top-left, top-right, right-bottom, bottom-left
			self.in_corners += [ln.intersect(sides[1], sides[2]), ln.intersect(sides[2], sides[3]), ln.intersect(sides[0], sides[3])]
			self.phase = 0
			# find corresponding points
			pts1 = np.float32(self.in_corners)
			pts2 = np.float32([(50, 50), (650, 50), (650, 650), (50, 650)])
			self.board_M = cv2.getPerspectiveTransform(pts1, pts2)
		self.draw()

def colval(col):
	coluint = np.uint8([[col]])
	hsv = cv2.cvtColor(coluint,cv2.COLOR_BGR2HSV)
	return hsv[0][0][2]


t = Test("test_images/chess5.jpg")
def render(*arg):
	t.draw()


def mousecb(evt, x, y, i, void):
	if evt == 1:
		t.click(x, y)

cv2.namedWindow('dst')
cv2.namedWindow('warp')
cv2.createTrackbar('black', 'warp', 0, 255, render)
cv2.setTrackbarPos('black', 'warp', 50)
cv2.createTrackbar('white', 'warp', 0, 255, render)
cv2.setTrackbarPos('white', 'warp', 175)
cv2.createTrackbar('ycutoff', 'warp', 0, 600/8, render)
cv2.setTrackbarPos('ycutoff', 'warp', 28)
cv2.setMouseCallback('dst', mousecb)

render()
while True:
	if cv2.waitKey(0) == 113:
		cv2.destroyAllWindows()
		break

