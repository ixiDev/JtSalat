package com.ixidev.jtsalat.utils


class PointF  {
    var x: Float = 0f
    var y: Float = 0f

    constructor()

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }


    /**
     * Create a new PointF initialized with the values in the specified
     * PointF (which is left unmodified).
     *
     * @param p The point whose values are copied into the new
     * point.
     */
    constructor(p: PointF) {
        this.x = p.x
        this.y = p.y
    }

    /**
     * Set the point's x and y coordinates
     */
    fun set(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    /**
     * Set the point's x and y coordinates to the coordinates of p
     */
    fun set(p: PointF) {
        this.x = p.x
        this.y = p.y
    }

    fun negate() {
        x = -x
        y = -y
    }

    fun offset(dx: Float, dy: Float) {
        x += dx
        y += dy
    }

    /**
     * Returns true if the point's coordinates equal (x,y)
     */
    fun equals(x: Float, y: Float): Boolean {
        return this.x == x && this.y == y
    }

}