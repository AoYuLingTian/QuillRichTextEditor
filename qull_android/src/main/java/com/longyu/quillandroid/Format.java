/**
 * Copyright (C) 2017 Pixel Dreamland LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.longyu.quillandroid;

/** Format
 * @author jkidi(Jakub Kidacki)
 */
public enum Format {
   BACKGROUND("background"),
   BOLD("bold"),
   COLOR("color"),
   FONT("font"),
   CODE("code"),
   ITALIC("italic"),
   LINK("link"),
   SIZE("size"),
   STRIKE("strike"),
   SCRIPT("script"),
   UNDERLINE("underline"),
   BLOCKQUOTE("blockquote"),
   HEADER("header"),
   INDENT("indent"),
   LIST("list"),
   ALIGN("align"),
   DIRECTION("direction"),
   CODE_BLOCK("code-block"),
   FORMULA("formula"),
   IMAGE("image"),
   VIDEO("video"),


   ORDERED("ordered"),//有序
   BULLET("bullet"),//无序
   FONT_SIZE("font-size"),
   TEXT("text");

   private String name;

   Format(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
