/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.bookshelf.model

import java.util.*

class BookUtil {
  private val imagesRootUrl =
    "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/"

  private val books = listOf(
    Book(
      "Crime and Punishment",
      "Fyodor Dostoevsky",
      "Classics",
      3.23f,
      "${imagesRootUrl}1382846449l/7144.jpg",
      2017
    ),
    Book(
      "Think and Grow Rich",
      "Napoleon Hill",
      "Business",
      5.17f,
      "${imagesRootUrl}1392024158l/815412.jpg",
      1992
    ),
    Book(
      "Hackers and Painters",
      "Paul Graham",
      "Nonfiction",
      3.06f,
      "${imagesRootUrl}1387653624l/6565257.jpg",
      1997
    ),
    Book(
      "Fight Club",
      "Chuck Palahniuk",
      "Fiction",
      3.18f,
      "${imagesRootUrl}1327866366l/825840.jpg",
      1989
    ),
    Book(
      "Masters of Doom",
      "David Kushner",
      "Nonfiction",
      2.28f,
      "${imagesRootUrl}1557932856l/222146._SY475_.jpg",
      2009
    ),
    Book(
      "1984",
      "George Orwell",
      "Classics",
      1.19f,
      "${imagesRootUrl}1557932856l/222146._SY475_.jpg",
      1993
    ),
    Book(
      "Fathers and Sons",
      "Ivan Turgenev",
      "Classics",
      4.96f,
      "${imagesRootUrl}1388798930l/373813.jpg",
      2012
    ),
    Book(
      "Hackers: Heroes of the Computer Revolution",
      "Steven Levy",
      "Nonfiction",
      2.15f,
      "${imagesRootUrl}1413406427l/1416856.jpg",
      1977
    ),
    Book(
      "The Prince",
      "Niccolo Machiavelli",
      "Classics",
      3.82f,
      "${imagesRootUrl}1390055828l/28862.jpg",
      2020
    ),
    Book(
      "How to Win Friends and Influence People",
      "Dale Carnegie",
      "Nonfiction",
      4.21f,
      "${imagesRootUrl}1442726934l/4865._SY475_.jpg",
      1996
    ),
    Book(
      "The Clean Coder",
      "Robert Martin",
      "Computer Science",
      4.28f,
      "${imagesRootUrl}1347470803l/10284614.jpg",
      1995
    ),
    Book(
      "Clean Code",
      "Robert Martin",
      "Computer Science",
      4.40f,
      "${imagesRootUrl}1436202607l/3735293._SX318_.jpg",
      2005
    ),
    Book(
      "The Alchemist",
      "Paulo Coelho",
      "Fiction",
      1.88f,
      "${imagesRootUrl}1483412266l/865.jpg",
      2002
    ),
    Book(
      "The Epic of Gilgamesh",
      "Anonymous",
      "Classics",
      5.70f,
      "${imagesRootUrl}1370840401l/19350.jpg",
      2009
    ),
    Book(
      "The Fall",
      "Albert Camus",
      "Fiction",
      3.02f,
      "${imagesRootUrl}1439035433l/219972._SY475_.jpg",
      2014
    ),
    Book(
      "Waiting for Godot",
      "Samuel Beckett",
      "Plays",
      1.83f,
      "${imagesRootUrl}1458410754l/772273._SY475_.jpg",
      2013
    ),
    Book(
      "The Prophet",
      "Khalil Gebran",
      "Poetry",
      5.22f,
      "${imagesRootUrl}1355046521l/2547.jpg",
      2014
    ),
    Book(
      "The Plague",
      "Albert Camus",
      "Fiction",
      5.01f,
      "${imagesRootUrl}1503362434l/11989._SY475_.jpg",
      1999
    ),
    Book(
      "The Birth of Tragedy",
      "Frederich Nietzsche",
      "Philosophy",
      1.98f,
      "${imagesRootUrl}1429743385l/2823.jpg",
      1999
    ),
    Book(
      "Brave New World",
      "Aldous Huxley",
      "Classics",
      4.99f,
      "${imagesRootUrl}1565335374l/996156._SY475_.jpg",
      2000
    ),
    Book(
      "The Great Gatsby",
      "F. Scott FitzGerald",
      "Classics",
      3.93f,
      "${imagesRootUrl}1565335374l/996156._SY475_.jpg",
      2007
    ),

    Book(
      "What Comes After",
      "Joanne Tompkins",
      "Classics",
      3.07f,
      "${imagesRootUrl}1597695077l/54610962.jpg",
      1981
    ),
    Book(
      "Too Good To Be True",
      "Carola Lovering",
      "Thriller",
      2.88f,
      "${imagesRootUrl}1607321994l/53137955.jpg",
      1983
    ),
    Book(
      "The Hunting Wives",
      "May Cobb",
      "Thriller",
      2.75f,
      "${imagesRootUrl}1601598187l/45714854.jpg",
      1987
    ),
    Book(
      "Empire of Pain",
      "Patrick Keefe",
      "Nonfiction",
      2.72f,
      "${imagesRootUrl}1611952534l/43868109.jpg",
      2001
    ),
    Book(
      "Code Complete",
      "Steve McConnell",
      "Computer Science",
      5.03f,
      "${imagesRootUrl}1396837641l/4845.jpg",
      2001
    ),
    Book(
      "Head First Design Patterns",
      "Eric Freeman",
      "Computer Science",
      5.28f,
      "${imagesRootUrl}1408309444l/58128.jpg",
      1992
    ),
    Book(
      "Effective Java",
      "Joshua Bloch",
      "Computer Science",
      1.51f,
      "${imagesRootUrl}1513389229l/34927404.jpg",
      1995
    ),
    Book(
      "Javascript: The Good Parts",
      "Douglas Crockford",
      "Computer Science",
      2.23f,
      "${imagesRootUrl}1328834793l/2998152.jpg",
      2019
    ),
    Book(
      "The C Programming Language",
      "Dennis Ritchie, Brian Kernighan",
      "Computer Science",
      1.5f,
      "${imagesRootUrl}1391032531l/515601.jpg",
      1994
    ),
  )

  fun getRandom(): Book {
    return books[Random().nextInt(books.size)]
  }
}