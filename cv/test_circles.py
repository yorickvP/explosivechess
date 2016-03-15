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
		dst = cv2.warpPerspective(self.orig_img, self.board_M, (600, 600))

		# detect and draw circles
		for (x, y, white) in detectcircles(dst):
			if white:
				cv2.circle(dst, (x, y), 25, (255, 255, 255), -1)
			else:
				cv2.circle(dst, (x, y), 25, (0, 0, 0), -1)

		# draw chess grid
		for i in range(1, 8):
			cv2.line(dst, ((600/8) * i, 0), ((600/8)*i, 600), (255, 0, 0), 2)
			cv2.line(dst, (0, (600/8) * i), (600, (600/8)*i), (255, 0, 0), 2)
		cv2.imshow('warp', dst)
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
			pts2 = np.float32([(0, 0), (600, 0), (600, 600), (0, 600)])
			self.board_M = cv2.getPerspectiveTransform(pts1, pts2)
		self.draw()

def colval(col):
	coluint = np.uint8([[col]])
	hsv = cv2.cvtColor(coluint,cv2.COLOR_BGR2HSV)
	return hsv[0][0][2]


def detectcircles(dst):
	# good values:
	# dp = 1, minDist = 54, param1 = 1, param2 = 13, minradius = 11, maxradius = 27
	edg2 = cv2.Canny(dst, 100, 210)
	g = cv2.cvtColor(dst, cv2.COLOR_BGR2GRAY)
	dp = cv2.getTrackbarPos('dp', 'warp')
	minDist = cv2.getTrackbarPos('minDist', 'warp')
	param1 = cv2.getTrackbarPos('param1', 'warp')
	param2 = cv2.getTrackbarPos('param2', 'warp')
	minradius = cv2.getTrackbarPos('minradius', 'warp')
	maxradius = cv2.getTrackbarPos('maxradius', 'warp')
	circles = cv2.HoughCircles(edg2, cv2.HOUGH_GRADIENT, dp, minDist,
	              param1=param1,
	              param2=param2,
	              minRadius=minradius,
	              maxRadius=maxradius)
	res = []
	if not circles is None:
		for (x, y, r) in circles[0]:
			mask = np.zeros(dst.shape[:2], np.uint8)
			cv2.circle(mask, (x, y), r, 255, -1)
			m = cv2.mean(dst, mask=mask)
			res.append((x, y, colval(m) > 99))
	return res

t = Test("test_images/chess2.jpg")
def render(*arg):
	t.draw()


def mousecb(evt, x, y, i, void):
	if evt == 1:
		t.click(x, y)

cv2.namedWindow('dst')
cv2.namedWindow('warp')
cv2.createTrackbar('dp', 'warp', 0, 10, render)
cv2.setTrackbarPos('dp', 'warp', 1)
cv2.createTrackbar('minDist', 'warp', 0, 500, render)
cv2.setTrackbarPos('minDist', 'warp', 54)
cv2.createTrackbar('param1', 'warp', 0, 500, render)
cv2.setTrackbarPos('param1', 'warp', 1)
cv2.createTrackbar('param2', 'warp', 0, 500, render)
cv2.setTrackbarPos('param2', 'warp', 13)
cv2.createTrackbar('minradius', 'warp', 0, 500, render)
cv2.setTrackbarPos('minradius', 'warp', 11)
cv2.createTrackbar('maxradius', 'warp', 0, 500, render)
cv2.setTrackbarPos('maxradius', 'warp', 27)
cv2.setMouseCallback('dst', mousecb)

render()
while True:
	if cv2.waitKey(0) == 113:
		cv2.destroyAllWindows()
		break

