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

#include <common.h>

LOCALE_IO::LOCALE_IO() {}
LOCALE_IO::~LOCALE_IO() {}

EDA_UNITS_T    g_UserUnit;

void wxStringSplit( const wxString& aText, wxArrayString& aStrings, wxChar aSplitter )
{
  unsigned start = 0, pos = 0, len = aText.Length();
  while(pos < len)
    if (aText[pos++] == aSplitter) {
      aStrings.Add(wxString(aText, start, pos-start-1));
      start = pos;
    }
  if (start != len)
    aStrings.Add(wxString(aText, start));
}
