// others

function increment(arg) {
    arg = arg + 1;
    return arg;
}

function sout(arg) {
    println("PRINT: " + arg);
    return arg;
}

function call(fc, name, arg) {
    f = fc[name];
    return f(arg);
}

function main() {
    function_collection = new();
    function_collection["increment"] = increment;
    function_collection["sout"] = sout;

    v1 = call(function_collection, "increment", 20);
    println(v1);

    v2 = call(function_collection, "sout", "SOMETHING");
    println(v2);
}