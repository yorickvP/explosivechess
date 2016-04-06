#!/usr/bin/env python
# -*- coding: utf8 -*-

import cv2
import numpy as np

from test import Test


GREEN = (0, 255, 0)
RED = (0, 0, 255)
WHITE = (255, 255, 255)
BLUEGREEN = (255, 255, 0)
BLUE = (255, 0, 0)
BLACK = (0, 0, 0)

CHESS_SIZE = 600

cap = cv2.VideoCapture(1)

class HistTest(Test):
	def __init__(self, filename):
		self.BORDER = 50
		Test.__init__(self, filename)
	def _draw_board(self, dst):
		hsvdst = cv2.cvtColor(dst,cv2.COLOR_BGR2HSV)
		step = CHESS_SIZE/8
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
				y = self.BORDER + step*i
				x = self.BORDER + step*j
				bl_cnt = np.sum(bl_dst[y-yc:y-yc + step,x:x+step] == 0)
				wt_cnt = np.sum(wt_dst[y-yc:y+step-yc,x:x+step] == 255)
				col[y-yc:y-yc+step,x:x+step,2] = np.zeros((step, step))
				s = str(bl_cnt) + "/" + str(wt_cnt)
				if bl_cnt > 500:
					cv2.circle(col, (x+step/2, y+step/2), 25, BLACK, -1)
				elif wt_cnt >= 75:
					cv2.circle(col, (x+step/2, y+step/2), 25, WHITE, -1)
				cv2.putText(col, s ,(x, y+step/2), font, 0.5,BLUEGREEN,2,cv2.LINE_AA)
	def update(self):
		# pass
		ret, frame = cap.read()
		self.orig_img = frame
		self.draw()

ret, frame = cap.read()
t = HistTest(frame)
t.run({
	"black": (0, 255, 21),
	"white": (0, 255, 227),
	"ycutoff": (0, CHESS_SIZE/8, 0)
})

