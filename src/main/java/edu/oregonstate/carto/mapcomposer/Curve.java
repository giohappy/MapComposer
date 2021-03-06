package edu.oregonstate.carto.mapcomposer;

import com.jhlabs.image.ImageMath;

public class Curve {

    public float[] x;
    public float[] y;

    public Curve() {
        x = new float[]{0, 1};
        y = new float[]{0, 1};
    }

    public Curve(Curve curve) {
        x = (float[]) curve.x.clone();
        y = (float[]) curve.y.clone();
    }

    /**
     * added by Bernie Jenny
     */
    public void setKnots(float[] kx, float[] ky) {
        this.x = new float[kx.length];
        this.y = new float[ky.length];
        System.arraycopy(kx, 0, this.x, 0, kx.length);
        System.arraycopy(ky, 0, this.y, 0, ky.length);
    }

    /**
     * added by Bernie Jenny
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.x.length; i++) {
            sb.append(x[i]);
            sb.append("\t");
            sb.append(y[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Scale curve from 0..255 to range 0..1 added by Bernie Jenny
     */
    public void normalize() {
        for (int i = 0; i < this.x.length; i++) {
            this.x[i] /= 255.f;
        }
        for (int i = 0; i < this.y.length; i++) {
            this.y[i] /= 255.f;
        }
    }

    public int addKnot(float kx, float ky) {
        int pos = -1;
        int numKnots = x.length;
        float[] nx = new float[numKnots + 1];
        float[] ny = new float[numKnots + 1];
        int j = 0;
        for (int i = 0; i < numKnots; i++) {
            if (pos == -1 && x[i] > kx) {
                pos = j;
                nx[j] = kx;
                ny[j] = ky;
                j++;
            }
            nx[j] = x[i];
            ny[j] = y[i];
            j++;
        }
        if (pos == -1) {
            pos = j;
            nx[j] = kx;
            ny[j] = ky;
        }
        x = nx;
        y = ny;
        return pos;
    }

    public void removeKnot(int n) {
        int numKnots = x.length;
        if (numKnots <= 2) {
            return;
        }
        float[] nx = new float[numKnots - 1];
        float[] ny = new float[numKnots - 1];
        int j = 0;
        for (int i = 0; i < numKnots - 1; i++) {
            if (i == n) {
                j++;
            }
            nx[i] = x[j];
            ny[i] = y[j];
            j++;
        }
        x = nx;
        y = ny;
        for (int i = 0; i < x.length; i++) {
            System.out.println(i + ": " + x[i] + " " + y[i]);
        }
    }

    private void sortKnots() {
        int numKnots = x.length;
        for (int i = 1; i < numKnots - 1; i++) {
            for (int j = 1; j < i; j++) {
                if (x[i] < x[j]) {
                    float t = x[i];
                    x[i] = x[j];
                    x[j] = t;
                    t = y[i];
                    y[i] = y[j];
                    y[j] = t;
                }
            }
        }
    }

    /**
     * Convert curve to array with 256 sampled values
     *
     * @param multiplier Optional array that is multiplied with the generated
     * array.
     * @return An array with 256 values between 0 and 255
     */
    protected int[] makeTable(int[] multiplier) {
        int numKnots = x.length;
        float[] nx = new float[numKnots + 2];
        float[] ny = new float[numKnots + 2];
        System.arraycopy(x, 0, nx, 1, numKnots);
        System.arraycopy(y, 0, ny, 1, numKnots);
        nx[0] = nx[1];
        ny[0] = ny[1];
        nx[numKnots + 1] = nx[numKnots];
        ny[numKnots + 1] = ny[numKnots];

        int[] table = new int[256];
        for (int i = 0; i < 1024; i++) {
            float f = i / 1024.0f;
            int x = (int) (255 * ImageMath.spline(f, nx.length, nx) + 0.5f);
            int y = (int) (255 * ImageMath.spline(f, nx.length, ny) + 0.5f);
            x = ImageMath.clamp(x, 0, 255);
            y = ImageMath.clamp(y, 0, 255);
            table[x] = y;
        }

        if (multiplier != null) {
            // avoid division by zero
            table[0] = (table[0] * multiplier[0]);

            // multiply tables and normalize with index
            for (int i = 1; i < 256; i++) {
                table[i] = (table[i] * multiplier[i]) / i;
            }
        }

        return table;
    }

    protected int[] makeTable() {
        return makeTable(null);
    }
}
