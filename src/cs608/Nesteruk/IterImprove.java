package cs608.Nesteruk;

public class IterImprove {
    /* initial locations */
    static int[][] flocs = {{1, 1}, {2, 5}, {4, 6}, {7, 7}, {9, 3}};
    /* four manhattan direction increments */
    static int[][] dirs = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    /**
     * Find the mean of an array of integers.
     * @param _c input array
     **/
    float mean(int[] _c) {
        int _ttl = 0;
        for (int _iv : _c)
            _ttl += _iv;
        return _ttl / (float)_c.length;
    }

    /**
     * Find the population variance of the passed array.  The population variance is the
     * average of the squares of differences of individual values from the mean of all values.
     * @param _c input array
     * @return the population variance of the inout array
     */
    float variance(int[] _c) {
        int n = _c.length;
        float mean = 0;
        for (int i = 0; i < n; i++) {
            mean += _c[i];
        }
        mean /= n;
        float variance = 0;
        for (int i = 0; i < n; i++) {
            float diff = _c[i] - mean;
            variance += diff * diff;
        }
        variance /=n;
        return variance;
    }
    /**
     * Find the mean n-dimensional coordinate in a list of coordinates.
     * @param _c n-dimensional input coordinates
     * @return the mean n-dimensional coordinate
     */
    float[] coord_mean(int[][] _c) {
        int _cl = _c.length;
        int _cd = _c[0].length;
        float[] _ttl = new float[_cd];
        for (int[] _iv : _c)
            for (int _ij = 0; _ij < _cd; ++_ij)
                _ttl[_ij] += _iv[_ij];
        for (int _ij = 0; _ij < _cd; ++_ij)
            _ttl[_ij] /= _cl;
        return _ttl;
    }

    /**
     * Display a list of n-dimensional integral coordinates.
     * @param _c input coordinate list
     */
    void coord_disp(int[][] _c) {
        int _cl = _c.length;
        int _cd = _c[0].length;
        if (_cd < 1)
            return;
        System.out.printf("(%d", _c[0][0]);
        for (int _ij = 1; _ij < _cd; ++_ij)
            System.out.printf(",%d", _c[0][_ij]);
        System.out.print(")");
        for (int _ii = 1; _ii < _cl; ++_ii) {
            System.out.printf(" (%d", _c[_ii][0]);
            for (int _ij = 1; _ij < _cd; ++_ij)
                System.out.printf(",%d", _c[_ii][_ij]);
            System.out.print(")");
        }
    }
    /**
     * Display an n-dimensional integral coordinate.  Create a list of one coordinate
     * and use coord_disp().
     * @param _c input n-dimensional coordinate
     */
    void coord_disp(int[] _c) {
        int[][] _cl = new int[1][];
        _cl[0] = _c;
        coord_disp(_cl);
    }
    /**
     * Display a list of n-dimensional float coordinates.
     * @param _c input coordinate list
     */
    void coord_disp(float[][] _c) {
        int _cl = _c.length;
        int _cd = _c[0].length;
        if (_cd < 1)
            return;
        System.out.printf("(%.2f", _c[0][0]);
        for (int _ij = 1; _ij < _cd; ++_ij)
            System.out.printf(",%.2f", _c[0][_ij]);
        System.out.print(")");
        for (int _ii = 1; _ii < _cl; ++_ii) {
            System.out.printf(" (%.2f", _c[_ii][0]);
            for (int _ij = 1; _ij < _cd; ++_ij)
                System.out.printf(",%.2f", _c[_ii][_ij]);
            System.out.print(")");
        }
    }
    /**
     * Display an n-dimensional float coordinate.  Create a list of one coordinate
     * and use coord_disp().
     * @param _c input n-dimensional coordinate
     */
    void coord_disp(float[] _c) {
        float[][] _cl = new float[1][];
        _cl[0] = _c;
        coord_disp(_cl);
    }
    /**
     * Convert a list of n-dimensional coordinates to lattice points (integer coordinates).
     * @param _c input coordinate list
     * @return corresponding list of lattice points
     */
    int[][] coord_lattice(float[][] _c) {
        final int _cl = _c.length;
        final int _cd = _c[0].length;
        int[][] _lp;
        _lp = new int[_cl][];
        for (int _ii = 0; _ii < _cl; ++_ii) {
            _lp[_ii] = new int[_cd];
            for (int _ij = 0; _ij < _cd; ++_ij)
                _lp[_ii][_ij] = (int) Math.floor(_c[_ii][_ij] + 0.5);
        }
        return _lp;
    }
    /**
     * Convert an n-dimensional coordinate to a lattice point.  Create a list of one coordinate
     * and use coord_lattice().
     * @param _c input n-dimensional coordinate
     * @return the corresponding lattice coordinate
     */
    int[] coord_lattice(float[] _c) {
        float[][] _cl = new float[1][];
        _cl[0] = _c;
        return coord_lattice(_cl)[0];
    }
    /**
     * Sum two n-dimensional coordinates.
     * @param _ad n-D left operand
     * @param _au n-D right operand
     * @return the coordinate sum
     */
    int[] coord_sum(int[] _ad, int[] _au) {
        int _cl = _ad.length;
        int[] _sc;
        if (_cl < 1)
            return null;
        _sc = new int[_cl];
        for (int _ij = 0; _ij < _cl; ++_ij)
            _sc[_ij] = _ad[_ij] + _au[_ij];
        return _sc;
    }
    /**
     * Find the manhattan distance between two n-dimensional lattice points.
     * @param _p1 first n-D point
     * @param _p2 second n-D point
     * @return the manhattan distance
     */
    int mhd(int[] _p1, int[] _p2) {
        int _dl = _p1.length;
        int _md = 0;
        for (int _ii = 0; _ii < _dl; ++_ii) {
            _md += Math.abs(_p1[_ii] - _p2[_ii]);
        }
        return _md;
    }
    /**
     * Find variance of manhattan distances to each of a list of points from an anchor point.
     * @param _grd anchor point
     * @param _locs n-D point list
     * @return the variance of the manhattan distances
     */
    float varl(int[] _grd, int[][] _locs) {
        int[] _vr = new int[0];
        _vr = new int[_locs.length];
        for (int i = 0; i < _locs.length; i++) {
            _vr[i] = mhd(_grd, _locs[i]);
        }
        return variance(_vr);
    }
    /**
     * Find the total manhattan distance from an anchor point to each of a list of points.
     * @param _grd anchor point
     * @param _locs n-D point list
     * @return the total manhattan distance
     */
    float mhdl(int[] _grd, int[][] _locs) {
        int _ttl = 0;
        for (int[] _loc : _locs) {
            _ttl += mhd(_loc, _grd);
        }
        return (float)_ttl;
    }
    /**
     * Incrementally improve the current manhattan distance, if possible.
     * @param best the current best point
     * @return the direction to a better point, or -1 for no improvement
     */
    int improve(int[] best, int[][] _flocs) {
        int[] nxt;
        float mhdist, mndist;
        int mhdir, dri;
        mndist = varl(best, _flocs);
        dri = mhdir = -1;
        for (int[] dr : dirs) {
            ++dri;
            nxt = coord_sum(best, dr);
            mhdist = varl(nxt, _flocs);
            if (mhdist < mndist) {
                mndist = mhdist;
                mhdir = dri;
            }
        }
        return mhdir;
    }
    /**
     * Manage the incremental improvement process.
     * @param _flocs list of fixed locations for optimization
     */
    void optimize(int[][] _flocs) {
        float[] cm;
        int[] best;
        int mhdir;
        boolean searching;
        cm = coord_mean(_flocs);
        System.out.print("mean coordinates ");
        coord_disp(cm);
        System.out.println();
        best = coord_lattice(cm);
        System.out.print("start ");
        coord_disp(best);
        System.out.printf(" opt %.2f\n", varl(best, _flocs));
        searching = true;
        while (searching) {
            // mhdir = improve_mhd(best, _flocs);
            mhdir = improve(best, _flocs);
            if (mhdir >= 0) {
                best = coord_sum(best, dirs[mhdir]);
                System.out.print("next ");
                coord_disp(best);
                System.out.printf(" opt %.2f\n", varl(best, _flocs));
            } else
                searching = false;
        }
    }
    public static void main(String[] args) {
        IterImprove obj = new IterImprove();
        System.out.print("friend locations ");
        obj.coord_disp(flocs);
        System.out.println();
        obj.optimize(flocs);
        System.out.print("complete");
    }
}