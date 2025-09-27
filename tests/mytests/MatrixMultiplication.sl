// unit test performing computation

function initMatrix(m, rows) {
    i = 0;
    while(i<rows) {
        m[i] = new();
        i = i + 1;
    }
}

function printMatrix(m) {
    i = 0;
    while(i<getSize(m)) {
        j = 0;
        s = "";
        while(j < getSize(m[i])) {
            s = s + m[i][j];
            if(j != getSize(m[i]) - 1) {
                s = s + " ";
            }
            j = j + 1;
        }
        println(s);
        i = i + 1;
    }
}

function multiplyMatrices(m1, m2) {
    m_result = new();

    i = 0;
    while(i<getSize(m1)) {
        j = 0;
        dotProduct = 0;
        m_result[i] = new();
        while(j<getSize(m2[0])) {
            k = 0;
            dotProduct = 0;
            while(k<getSize(m2)) {
                dotProduct = dotProduct + m1[i][k] * m2[k][j];
                k = k + 1;
            }
            m_result[i][j] = dotProduct;
            j = j + 1;
        }
        i = i + 1;
    }

    printMatrix(m_result);
}

function main() {
    m1 = new();
    m2 = new();

    initMatrix(m1, 2);
    initMatrix(m2, 3);

    m1[0][0] = 1; // 2x3 matrix
    m1[0][1] = 2; // 1 2 3
    m1[0][2] = 3; // 4 5 6
    m1[1][0] = 4;
    m1[1][1] = 5;
    m1[1][2] = 6;

    m2[0][0] = 7; // 3x2 matrix
    m2[0][1] = 8; // 7 8
    m2[1][0] = 9; // 9 10
    m2[1][1] = 10; // 11 12
    m2[2][0] = 11;
    m2[2][1] = 12;

    multiplyMatrices(m1, m2); 
    // resulting matrix
    // 58 64
    // 139 154

    println("");
    multiplyMatrices(m2, m1); 
    // resulting matrix
    // 39 54 69
    // 49 68 87
    // 59 82 105
}