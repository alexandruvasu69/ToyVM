// unit test for strings

function main() {
    s1 = "abcdef";

    println(subString(s1, 1, 3));
    println(subString(s1, 0, 6));
    println(subString(s1, 5, 6));
    println(subString(s1, 5, 5));
    println(subString(s1, 0, 0));

    sub1 = subString(s1, 2, 5);
    println(subString(sub1, 1, 2));

    s1 = s1 + subString(s1, 1, 3);
    println(s1);

    println(subString(subString(s1, 1, 4), 2, 3));

    println(subString(s1, 0, 10));
}