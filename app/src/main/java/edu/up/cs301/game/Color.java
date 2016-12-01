package edu.up.cs301.game;

import java.io.Serializable;

/**
 * Created by Eric Imperio on 11/30/2016.
 */

public class Color implements Serializable {
        public static final int BLACK       = 0xFF000000;
        public static final int DKGRAY      = 0xFF444444;
        public static final int GRAY        = 0xFF888888;
        public static final int LTGRAY      = 0xFFCCCCCC;
        public static final int WHITE       = 0xFFFFFFFF;
        public static final int RED         = 0xFFFF0000;
        public static final int GREEN       = 0xFF00FF00;
        public static final int BLUE        = 0xFF0000FF;
        public static final int YELLOW      = 0xFFFFFF00;
        public static final int CYAN        = 0xFF00FFFF;
        public static final int MAGENTA     = 0xFFFF00FF;
        public static final int TRANSPARENT = 0;
        private static final long serialVersionUID = 240959328750366457L;

        /**
         * Return the alpha component of a color int. This is the same as saying
         * color >>> 24
         */
        public static int alpha(int color) {
            return color >>> 24;
        }

        /**
         * Return the red component of a color int. This is the same as saying
         * (color >> 16) & 0xFF
         */
        public static int red(int color) {
            return (color >> 16) & 0xFF;
        }

        /**
         * Return the green component of a color int. This is the same as saying
         * (color >> 8) & 0xFF
         */
        public static int green(int color) {
            return (color >> 8) & 0xFF;
        }

        /**
         * Return the blue component of a color int. This is the same as saying
         * color & 0xFF
         */
        public static int blue(int color) {
            return color & 0xFF;
        }

        /**
         * Return a color-int from red, green, blue components.
         * The alpha component is implicity 255 (fully opaque).
         * These component values should be [0..255], but there is no
         * range check performed, so if they are out of range, the
         * returned color is undefined.
         * @param red  Red component [0..255] of the color
         * @param green Green component [0..255] of the color
         * @param blue  Blue component [0..255] of the color
         */
        public static int rgb(int red, int green, int blue) {
            return (0xFF << 24) | (red << 16) | (green << 8) | blue;
        }

        /**
         * Return a color-int from alpha, red, green, blue components.
         * These component values should be [0..255], but there is no
         * range check performed, so if they are out of range, the
         * returned color is undefined.
         * @param alpha Alpha component [0..255] of the color
         * @param red   Red component [0..255] of the color
         * @param green Green component [0..255] of the color
         * @param blue  Blue component [0..255] of the color
         */
        public static int argb(int alpha, int red, int green, int blue) {
            return (alpha << 24) | (red << 16) | (green << 8) | blue;
        }
}
