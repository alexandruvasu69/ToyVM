// unit test for objects (objects/arrays)

function main() {
    obj1 = new();
    obj1["name"] = "Alex";
    obj1["age"] = 20;
    obj1["uni"] = "TUE";

    obj2 = new();
    obj2["name"] = "Alex";
    obj2["age"] = 21;
    obj2["uni"] = "TUE";
    obj2["programme"] = "masters";

    tags = new();
    tags[0] = "name";
    tags[1] = "age";
    tags[2] = "uni";
    tags[3] = "programme";

    i = 0;
    merged = new();
    while(i < getSize(tags)) {
        tag = tags[i];
        if(hasProperty(obj1, tag)) {
            merged[tag] = obj1[tag];
        } 
        
        if(hasProperty(obj2, tag)) {
            merged[tag] = obj2[tag];
        }
        
        i = i + 1;
    }

    println("Name: " + merged["name"]);
    println("Age: " + merged["age"]);
    println("Uni: " + merged["uni"]);
    println("Programme: " + merged["programme"]);
}