// unit test for objects (objects/arrays)

function printArr(arr) {
    i = 0;
    while(i < getSize(arr)) {
        println(i + ": " + arr[i]);
        i = i + 1;
    }
}

function main() {
    obj = new();
    obj["a"] = new();
    obj["b"] = new();

    arr1 = obj["a"];
    arr2 = obj["b"];

    i = 0;
    while(i < 5) {
        arr1[i] = i + 1;
        i = i + 1;
    }

    arr1[0] = 1;
    arr1[1] = 2;
    arr1[2] = 3;

    arr2[0] = 6;
    arr2[1] = 7;
    arr2[2] = 8;
    arr2["3"] = 9;
    arr2["0"] = 10;

    println("Arr1");
    printArr(obj.a);
    println("Arr2");
    printArr(obj.b);

    obj.a[-1] = 99;
    println(getSize(obj.a));
    i = -1;
    while(i < getSize(obj.a) - 1) {
        println(i + ": " + obj.a[i]);
        i = i + 1;
    }
}