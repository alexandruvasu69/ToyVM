// unit test for strings

function main() {
    s1 = "x=" + 1 + 2;
    println(s1);

    s2 = 1 + 2 + "=y";
    println(s2);

    a = 2;
    b = 3;
    s3 = "x=" + (a + b);
    println(s3);

    s4 = "true=" + true + ", false=" + false;
    println(s4);

    s5 = "number=" + 42 + ", string=" + "something" + ", boolean=" + true;
    println(s5);

    s6 = "";
    i = 0;
    while(i<5) {
        s6 = s6 + (i+1);
        i = i + 1;
    }

    println(s6);

    arr = new();
    arr[0] = "a";
    arr[1] = "b";
    arr[2] = "c";
    arr[3] = "d";
    arr[4] = "e";
    arr[5] = "f";

    s7 = "";
    i = 0;
    while(i<getSize(arr)) {
        s7 = s7 + arr[i];
        i = i + 1;
    }

    println(s7);
}