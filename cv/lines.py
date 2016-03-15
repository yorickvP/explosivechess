# -*- coding: utf8 -*-


import numpy as np


def linetopoints(rho, theta):
	a = np.cos(theta)
	b = np.sin(theta)
	x0 = a*rho
	y0 = b*rho
	x1 = int(x0 + 1000*(-b))
	y1 = int(y0 + 1000*(a))
	x2 = int(x0 - 1000*(-b))
	y2 = int(y0 - 1000*(a))
	return (x1, y1), (x2, y2)

def linexfromy(rho, theta, y):
	a, b = (np.cos(theta), np.sin(theta))
	x0, y0 = (a * rho, b * rho)
	return x0 + ((y - y0) / a) * (-b)

def lineyfromx(rho, theta, x):
	a, b = (np.cos(theta), np.sin(theta))
	x0, y0 = (a * rho, b * rho)
	return y0 + ((x - x0) / (-b)) * a

# AX = b, where

# A = [cos θ1  sin θ1]   b = |r1|   X = |x|
#     [cos θ2  sin θ2]       |r2|       |y|

def intersect((r1, theta1), (r2, theta2)):
	# thanks stackoverflow; http://stackoverflow.com/questions/383480/intersection-of-two-lines-defined-in-rho-theta-parameterization
	ct1 = np.cos(theta1)
	st1 = np.sin(theta1)
	ct2 = np.cos(theta2)
	st2 = np.sin(theta2)
	d = ct1 * st2 - st1 * ct2 # determinative (rearranged matrix for inverse)
	if d != 0:
		x = int((st2 * r1 - st1 * r2) / d)
		y = int((-ct2 * r1 + ct1 * r2) / d)
		return (x, y)
	else: # lines are parallel
		return None

def findlineleftof(lines, x, y):
	m = (None, float("infinity"))
	for [[rho, theta]] in lines:
		x_line = linexfromy(rho, theta, y)
		if x > x_line and x - x_line < m[1]:
			m = ((rho, theta), x - x_line)
	return m[0]
def findlinetopof(lines, x, y):
	m = (None, float("infinity"))
	for [[rho, theta]] in lines:
		y_line = lineyfromx(rho, theta, x)
		if y > y_line and y - y_line < m[1]:
			m = ((rho, theta), y - y_line)
	return m[0]


def findlinerightof(lines, x, y):
	m = (None, float("infinity"))
	for [[rho, theta]] in lines:
		x_line = linexfromy(rho, theta, y)
		if x < x_line and x_line - x  < m[1]:
			m = ((rho, theta), x_line - x)
	return m[0]
def findlinebottomof(lines, x, y):
	m = (None, float("infinity"))
	for [[rho, theta]] in lines:
		y_line = lineyfromx(rho, theta, x)
		if y < y_line and y_line - y  < m[1]:
			m = ((rho, theta), y_line - y)
	return m[0]

