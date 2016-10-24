/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 AgileApes, Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mmnaseri.projects.renamex.str;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/23/13, 12:03 PM)
 */
public class TaggedToken implements Token {

    private final Token token;
    private final int tag;

    public TaggedToken(Token token) {
        this(token, NO_TAG);
    }

    public TaggedToken(Token token, int tag) {
        this.token = token;
        this.tag = tag;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public int getStart() {
        return token.getStart();
    }

    @Override
    public int getEnd() {
        return token.getEnd();
    }

    @Override
    public int getMargin() {
        return token.getMargin();
    }

    @Override
    public int getLength() {
        return token.getLength();
    }

    @Override
    public boolean isTagged() {
        return tag != NO_TAG;
    }

}