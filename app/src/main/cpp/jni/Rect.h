/*
 * This file is part of the Gerberoid project.
 *
 * Copyright (C) 2017 Marcus Comstedt <marcus@mc.pp.se>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef RECT_H__
#define RECT_H__ 1

#include "jniref.h"
#include <cstdint>

namespace android
{

class Rect : public JNIRef
{
  friend class RectF;

  using JNIRef::JNIRef;

 private:
  class Native;

 public:
  Rect(int left, int top, int right, int bottom);
};

class RectF : public JNIRef
{
  using JNIRef::JNIRef;

 public:
  RectF(float left, float top, float right, float bottom);
};

}

#endif // RECT_H__
