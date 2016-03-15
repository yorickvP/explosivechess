#!/usr/bin/env python
# -*- coding: utf8 -*-

import cv2
import numpy as np
import lines as ln

from test import Test


GREEN = (0, 255, 0)
RED = (0, 0, 255)
WHITE = (255, 255, 255)
BLUEGREEN = (255, 255, 0)
BLUE = (255, 0, 0)
BLACK = (0, 0, 0)

CHESS_SIZE = 600


class CircleTest(Test):
	def __init__(self, filename):
		self.BORDER = 0
		Test.__init__(self, filename)
	def _draw_board(self, dst):
		# detect and draw circles
		for (x, y, white) in detectcircles(dst):
			if white:
				cv2.circle(dst, (x, y), 25, WHITE, -1)
			else:
				cv2.circle(dst, (x, y), 25, BLACK, -1)

		self.drawgrid(dst)

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

t = CircleTest("test_images/chess2.jpg")

t.run({
	'dp': (0, 10, 1),
	'minDist': (0, 500, 54),
	'param1': (0, 500, 1),
	'param2': (0, 500, 13),
	'minradius': (0, 500, 11),
	'maxradius': (0, 500, 27)
})
