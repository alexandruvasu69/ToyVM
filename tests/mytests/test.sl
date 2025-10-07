function printObj(obj, i) {
    print(obj[i]);
}

function main() {
    obj = new();
    obj[0] = 0;
    obj[1] = 1;
    obj[2] = 2;
    obj[3] = 3;

    i = 0;
    while(i != 4) {
        printObj(obj, i);
        i = i + 1;
    }
}