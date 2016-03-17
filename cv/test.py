#!/usr/bin/env python
# -*- coding: utf8 -*-

import cv2
import numpy as np
import lines as ln


GREEN = (0, 255, 0)
RED = (0, 0, 255)
WHITE = (255, 255, 255)
BLUEGREEN = (255, 255, 0)
BLUE = (255, 0, 0)
BLACK = (0, 0, 0)

CHESS_SIZE = 600

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
				cv2.line(img,(x1,y1),(x2,y2),GREEN,2)
			else:
				cv2.line(img,(x1,y1),(x2,y2),RED,2)
		for (x, y) in self.in_corners:
			cv2.circle(img, (x, y), 5, BLUE, -1)
		cv2.imshow('dst', img)
	def draw_board(self):
		dst = cv2.warpPerspective(self.orig_img, self.board_M, (CHESS_SIZE + 2*self.BORDER, CHESS_SIZE + 2*self.BORDER))
		self._draw_board(dst)
		cv2.imshow('warp', dst)
	def drawgrid(self, dst):
		# draw chess grid
		step = CHESS_SIZE / 8
		for i in range(1, 8):
			cv2.line(dst, (self.BORDER + step * i, self.BORDER + 0), (self.BORDER + step*i, self.BORDER + CHESS_SIZE), (255, 0, 0), 2)
			cv2.line(dst, (self.BORDER + 0, self.BORDER + step * i), (self.BORDER + CHESS_SIZE, self.BORDER + step*i), (255, 0, 0), 2)
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
			pts2 = np.float32([(self.BORDER, self.BORDER), (CHESS_SIZE+self.BORDER, self.BORDER), (CHESS_SIZE+self.BORDER, CHESS_SIZE+self.BORDER), (self.BORDER, CHESS_SIZE+self.BORDER)])
			self.board_M = cv2.getPerspectiveTransform(pts1, pts2)
		self.draw()
	def run(self, trackbars):
		def mousecb(evt, x, y, i, void):
			if evt == 1:
				self.click(x, y)

		self.draw()
		cv2.namedWindow('dst')
		cv2.namedWindow('warp')
		for (key, (min, max, cur)) in trackbars.items():
			cv2.createTrackbar(key, 'warp', min, max, lambda *x: self.draw())
			cv2.setTrackbarPos(key, 'warp', cur)
		cv2.setMouseCallback('dst', mousecb)
		while True:
			if cv2.waitKey(0) == 113:
				cv2.destroyAllWindows()
				break


