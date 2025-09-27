// unit test for strings

function printArr(arr) {
    i = 0;
    while(i<getSize(arr)) {
        println("Substr " + i + ": " + arr[i]);
        i = i + 1;
    }
    println("");
}

function main() {
    s1 = "base";
    printArr(stringSplit(s1));

    s2 = "base case";
    printArr(stringSplit(s2));

    s3 = "comma,test";
    printArr(stringSplit(s3));

    s4 = "a ab  abc   abcd";
    printArr(stringSplit(s4));

    s5 = " this  is   an example        ";
    arr1 = stringSplit(s5);
    printArr(arr1);

    i = 0;
    s6 = "";
    while(i<getSize(arr1)) {
        if(arr1[i] != " ") {
            s6 = s6 + arr1[i];
        }
        i = i + 1;
    }

    println(s6);

    s7 = "";
    printArr(stringSplit(s7));

}