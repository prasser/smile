/*******************************************************************************
 * Copyright (c) 2010 Haifeng Li
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package smile.math.matrix;

import smile.math.Math;

/**
 * An abstract interface of dense matrix.
 *
 * @author Haifeng Li
 */
public abstract class DenseMatrix extends Matrix implements MatrixMultiplication<DenseMatrix, DenseMatrix> {
    /**
     * Set the entry value at row i and column j.
     */
    public abstract double set(int i, int j, double x);

    /**
     * Set the entry value at row i and column j. For Scala users.
     */
    public double update(int i, int j, double x) {
        return set(i, j, x);
    }

    /**
     * Returns the matrix transpose.
     */
    public abstract DenseMatrix transpose();

    /**
     * Returns the inverse matrix.
     */
    DenseMatrix inverse() {
        return inverse(false);
    }

    /**
     * Returns the inverse matrix.
     * @param inPlace if true, this matrix will be used for matrix decomposition.
     */
    DenseMatrix inverse(boolean inPlace) {
        DenseMatrix a = inPlace ? this : copy();
        if (nrows() == ncols()) {
            LUDecomposition lu = new LUDecomposition(a);
            return lu.inverse();
        } else {
            QRDecomposition qr = new QRDecomposition(a);
            return qr.inverse();
        }
    }
    /**
     * Returns a copy of this matrix.
     */
    public abstract DenseMatrix copy();

    @Override
    public abstract DenseMatrix ata();

    @Override
    public abstract DenseMatrix aat();

    /**
     * A[i][j] += x
     */
    public abstract double add(int i, int j, double x);

    /**
     * A[i][j] -= x
     */
    public abstract double sub(int i, int j, double x);

    /**
     * A[i][j] *= x
     */
    public abstract double mul(int i, int j, double x);

    /**
     * A[i][j] /= x
     */
    public abstract double div(int i, int j, double x);

    /**
     * C = A + B
     * @return the result matrix
     */
    public DenseMatrix add(DenseMatrix b, DenseMatrix c) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) + b.get(i, j));
            }
        }
        return c;
    }

    /**
     * In place addition A = A + B
     * @return this matrix
     */
    public DenseMatrix add(DenseMatrix b) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                add(i, j, b.get(i, j));
            }
        }
        return this;
    }

    /**
     * C = A - B
     * @return the result matrix
     */
    public DenseMatrix sub(DenseMatrix b, DenseMatrix c) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) - b.get(i, j));
            }
        }
        return c;
    }

    /**
     * In place subtraction A = A - B
     * @return this matrix
     */
    public DenseMatrix sub(DenseMatrix b) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sub(i, j, b.get(i, j));
            }
        }
        return this;
    }

    /**
     * C = A * B
     * @return the result matrix
     */
    public DenseMatrix mul(DenseMatrix b, DenseMatrix c) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) * b.get(i, j));
            }
        }
        return c;
    }

    /**
     * In place element-wise multiplication A = A * B
     * @return this matrix
     */
    public DenseMatrix mul(DenseMatrix b) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mul(i, j, b.get(i, j));
            }
        }
        return this;
    }

    /**
     * C = A / B
     * @return the result matrix
     */
    public DenseMatrix div(DenseMatrix b, DenseMatrix c) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) / b.get(i, j));
            }
        }
        return c;
    }

    /**
     * In place element-wise division A = A / B
     * A = A - B
     * @return this matrix
     */
    public DenseMatrix div(DenseMatrix b) {
        if (nrows() != b.nrows() || ncols() != b.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                div(i, j, b.get(i, j));
            }
        }
        return this;
    }

    /**
     * Element-wise addition C = A + x
     */
    public DenseMatrix add(double x, DenseMatrix c) {
        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) + x);
            }
        }

        return c;
    }

    /**
     * In place element-wise addition A = A + x
     */
    public DenseMatrix add(double x) {
        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                add(i, j, x);
            }
        }

        return this;
    }

    /**
     * Element-wise addition C = A - x
     */
    public DenseMatrix sub(double x, DenseMatrix c) {
        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) - x);
            }
        }

        return c;
    }

    /**
     * In place element-wise subtraction A = A - x
     */
    public DenseMatrix sub(double x) {
        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sub(i, j, x);
            }
        }

        return this;
    }

    /**
     * Element-wise addition C = A * x
     */
    public DenseMatrix mul(double x, DenseMatrix c) {
        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) * x);
            }
        }

        return c;
    }

    /**
     * In place element-wise multiplication A = A * x
     */
    public DenseMatrix mul(double x) {
        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mul(i, j, x);
            }
        }

        return this;
    }

    /**
     * Element-wise addition C = A / x
     */
    public DenseMatrix div(double x, DenseMatrix c) {
        if (nrows() != c.nrows() || ncols() != c.ncols()) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }

        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                c.set(i, j, get(i, j) / x);
            }
        }

        return c;
    }

    /**
     * In place element-wise division A = A / x
     */
    public DenseMatrix div(double x) {
        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                div(i, j, x);
            }
        }

        return this;
    }

    /**
     * Replaces NaN's with given value.
     */
    public DenseMatrix replaceNaN(double x) {
        int m = nrows();
        int n = ncols();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (Double.isNaN(get(i, j))) {
                    set(i, j, x);
                }
            }
        }

        return this;
    }

    /**
     * Returns the sum of all elements in the matrix.
     * @return the sum of all elements.
     */
    public double sum() {
        int m = nrows();
        int n = ncols();

        double s = 0.0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                s += get(i, j);
            }
        }

        return s;
    }

    /**
     * Return the two-dimensional array of matrix.
     * @return the two-dimensional array of matrix.
     */
    public double[][] array() {
        double[][] V = new double[nrows()][ncols()];
        for (int i = 0; i < nrows(); i++) {
            for (int j = 0; j < ncols(); j++) {
                V[i][j] = get(i, j);
            }
        }
        return V;
    }

    /**
     * Returns the string representation of matrix.
     * @param full Print the full matrix if true. Otherwise only print top left 7 x 7 submatrix.
     */
    public String toString(boolean full) {
        StringBuilder sb = new StringBuilder();
        final int fields = 7;
        int m = Math.min(fields, nrows());
        int n = Math.min(fields, ncols());

        String newline = n < ncols() ? "...\n" : "\n";

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%8.4f  ", get(i, j)));
            }
            sb.append(newline);
        }

        if (m < nrows()) {
            sb.append("  ...\n");
        }

        return sb.toString();
    }
}
