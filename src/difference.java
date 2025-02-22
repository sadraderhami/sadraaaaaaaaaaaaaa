import java.util.*;


class JSONValue {
    public boolean isValid = true;
    public int size = 0;

    public static boolean isReal(String string) {///  resolve negative numbers
        if (string.charAt(0) == '-') {
            string = string.substring(1);
        }
        if (!string.contains(".")) {
            string = string + ".";
        }
        boolean delimiterMatched = false;
        char delimiter = '.';
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!(c >= '0' && c <= '9' || c == delimiter)) {
                // contains not number
                return false;
            }
            if (c == delimiter) {
                // delimiter matched twice
                if (delimiterMatched) {
                    return false;
                }
                delimiterMatched = true;
            }
        }
        // if matched delimiter once return true
        return delimiterMatched;
    }

    public static String mark(String str) {
        String str2 = str.replaceAll("\\\\\"", "cc");
        String marked = "";
        boolean isInString = false;
        for (int i = 0; i < str2.length(); i++) {
            if (str2.charAt(i) == '\"') {
                isInString = !isInString;
            }
            if (isInString) {
                marked += '\"';
            } else {
                marked += str2.charAt(i);
            }
        }
        return marked;
    }

    public static String takingAnd(String str) {
        str = str.replaceAll("\\\\\"", "cc");
        String res = "";
        String marked = mark(str);
        boolean isInString = false;
        for (int i = 0; i < str.length(); i++) {
            if (marked.charAt(i) == '\"') {
                isInString = !isInString;
            }
            if (str.charAt(i) == marked.charAt(i)) {
                res += str.charAt(i);
            } else {
                if (str.charAt(i) == '\\') {
                    res += '\\';
                } else {
                    res += '0';
                }
            }
        }
        return res;
    }

    public static String cleanUp(String str) {
        String marked = mark(str);
        for (int i = 0; i < str.length(); i++) {
            if (marked.charAt(i) == ' ') {
                str = str.substring(0, i) + str.substring(i + 1);
                marked = marked.substring(0, i) + marked.substring(i + 1);
                i--;
            }
        }
        return str;
    }

    public boolean hasOnly(String str, char c, int from) {
        for (int i = from; i < str.length(); i++) {
            if (str.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }

    public void handleInsideQs(String str) {
        str = cleanUp(str);
        String anded = takingAnd(str);
        if (anded.contains("\"\"")) {
            this.isValid = false;
            return;
        }
        anded = anded.replace("\\\\", "bb");
        if (anded.contains("\\")) {
            this.isValid = false;
            return;
        }
    }
}

class JSONArray extends JSONValue {
    public ArrayList<String> values;

    JSONArray(String str) {
        str = cleanUp(str);
        if (str.contains("\\")) {
            if (!str.startsWith("\\", str.indexOf("\\") + 1) && !str.startsWith("\"", str.indexOf("\\") + 1)) {
                isValid = false;
                return;
            }
        }
        if (str.charAt(0) != '[' || str.charAt(str.length() - 1) != ']') {
            isValid = false;
            return;
        }//isValid test
        str = str.substring(1, str.length() - 1);
        //str --> "b",true,{"a":false},[235,{},{"d":null}]
        values = new ArrayList<>();
        while (!str.isEmpty()) {
            int commaIndex;
            if (mark(str).charAt(0) == '\"') {
                if (mark(str).contains(",")) {
                    commaIndex = mark(str).indexOf(",");
                } else {
                    if (hasOnly(mark(str), '\"', 0)) {
                        commaIndex = str.length();
                    } else {
                        isValid = false;
                        return;
                    }
                }
                String firstValue = str.substring(0, commaIndex);
                values.add(firstValue);
                if (commaIndex == str.length()) {
                    str = "";
                } else {
                    str = str.substring(commaIndex + 1);
                }
            } else if (mark(str).charAt(0) == '{') {
                if (!mark(str).contains("}")) {
                    isValid = false;
                    return;
                }///isValid test
                int open = 0;
                int close = 0;
                commaIndex = 0;
                for (int i = 0; i < str.length(); i++) {
                    if (mark(str).charAt(i) == '{') {
                        open++;
                    }
                    if (mark(str).charAt(i) == '}') {
                        close++;
                        if (open == close) {
                            commaIndex = i + 1;
                            break;
                        }
                    }
                }
                if (commaIndex == 0) {
                    isValid = false;
                    return;
                }///isValid test
                String firstValue = str.substring(0, commaIndex);
                values.add(firstValue);
                if (commaIndex == str.length()) {
                    str = "";
                } else {
                    str = str.substring(commaIndex + 1);
                }
            } else if (str.charAt(0) == '[') {
                if (!str.contains("]")) {
                    isValid = false;
                    return;
                }///isValid test
                int open = 0;
                int close = 0;
                commaIndex = 0;
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == '[') {
                        open++;
                    }
                    if (str.charAt(i) == ']') {
                        close++;
                        if (open == close) {
                            commaIndex = i + 1;
                            break;
                        }
                    }
                }
                if (commaIndex == 0) {
                    isValid = false;
                    return;
                }///isValid test
                String firstValue = str.substring(0, commaIndex);
                values.add(firstValue);
                if (commaIndex == str.length()) {
                    str = "";
                } else {
                    str = str.substring(commaIndex + 1);
                }

            } else {
                if (str.contains(",")) {
                    commaIndex = str.indexOf(",");
                } else {
                    commaIndex = str.length();
                }
                if (str.startsWith("null", 0)) {
                    values.add("null");
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else if (str.startsWith("true", 0)) {
                    values.add("true");
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else if (str.startsWith("false", 0)) {
                    values.add("false");
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else if (isReal(str.substring(0, commaIndex))) {
                    values.add(str.substring(0, commaIndex));
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else {
                    isValid = false;
                    return;
                }///isValid test
            }
        }
        size = values.size();
    }
}

class JSONObject extends JSONValue {
    public HashMap<String, String> values;

    JSONObject(String str) {
        str = cleanUp(str);
        if (str.contains("\\")) {
            if (!str.startsWith("\\", str.indexOf("\\") + 1) && !str.startsWith("\"", str.indexOf("\\") + 1)) {
                isValid = false;
                return;
            }
        }
        if (str.charAt(0) != '{' || str.charAt(str.length() - 1) != '}') {
            isValid = false;
            return;
        }///isValid test
        str = str.substring(1, str.length() - 1);
        //str --> "sa{"3]":"b","bd":true,"ab":{"a":false},"li":[235,{},{"d":null}]
        values = new HashMap<>();
        while (!str.isEmpty()) {
            if (mark(str).charAt(0) != '\"' || !mark(str).contains(":")) {
                isValid = false;
                return;
            }///isValid test
            int colonIndex = mark(str).indexOf(":");
            int commaIndex = 0;
            String firstKey = str.substring(0, colonIndex);
            //firstKey = "sa{"3]"

            if (mark(str).charAt(colonIndex + 1) == '\"') {
                if (mark(str).contains(",")) {
                    commaIndex = mark(str).indexOf(",");
                } else {
                    if (hasOnly(mark(str), '\"', colonIndex + 1)) {
                        commaIndex = str.length();
                    } else {
                        isValid = false;
                        return;
                    }
                }
                String firstValue = str.substring(colonIndex + 1, commaIndex);
                values.put(firstKey, firstValue);
                if (commaIndex == str.length()) {
                    str = "";
                } else {
                    str = str.substring(commaIndex + 1);
                }
            } else if (mark(str).charAt(colonIndex + 1) == '{') {
                if (!mark(str).contains("}")) {
                    isValid = false;
                    return;
                }///isValid test
                int open = 0;
                int close = 0;
                commaIndex = 0;
                for (int i = colonIndex + 1; i < str.length(); i++) {
                    if (mark(str).charAt(i) == '{') {
                        open++;
                    }
                    if (mark(str).charAt(i) == '}') {
                        close++;
                        if (open == close) {
                            commaIndex = i + 1;
                            break;
                        }
                    }
                }
                if (commaIndex == 0) {
                    isValid = false;
                    return;
                }///isValid test
                String firstValue = str.substring(colonIndex + 1, commaIndex);
                values.put(firstKey, firstValue);
                if (commaIndex == str.length()) {
                    str = "";
                } else {
                    str = str.substring(commaIndex + 1);
                }
            } else if (mark(str).charAt(colonIndex + 1) == '[') {
                if (!mark(str).contains("]")) {
                    isValid = false;
                    return;
                }///isValid test
                int open = 0;
                int close = 0;
                commaIndex = 0;
                for (int i = colonIndex + 1; i < str.length(); i++) {
                    if (mark(str).charAt(i) == '[') {
                        open++;
                    }
                    if (mark(str).charAt(i) == ']') {
                        close++;
                        if (open == close) {
                            commaIndex = i + 1;
                            break;
                        }
                    }
                }
                if (commaIndex == 0) {
                    isValid = false;
                    return;
                }///isValid test
                String firstValue = str.substring(colonIndex + 1, commaIndex);
                values.put(firstKey, firstValue);
                if (commaIndex == str.length()) {
                    str = "";
                } else {
                    str = str.substring(commaIndex + 1);
                }
            } else {
                if (str.contains(",")) {
                    commaIndex = str.indexOf(",");
                } else {
                    commaIndex = str.length();
                }
                if (str.startsWith("null", colonIndex + 1)) {
                    values.put(firstKey, "null");
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else if (str.startsWith("true", colonIndex + 1)) {
                    values.put(firstKey, "true");
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else if (str.startsWith("false", colonIndex + 1)) {
                    values.put(firstKey, "false");
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else if (isReal(str.substring(colonIndex + 1, commaIndex))) {
                    values.put(firstKey, str.substring(colonIndex + 1, commaIndex));
                    if (commaIndex == str.length()) {
                        str = "";
                    } else {
                        str = str.substring(commaIndex + 1);
                    }
                } else {
                    isValid = false;
                    return;
                }///isValid test
            }
        }
        size = values.size();
    }
}

class XMLValue {
    public boolean isValid = true;
    public int size = 0;

    public static boolean isReal(String string) {///  resolve negative numbers
        if (string.charAt(0) == '-') {
            string = string.substring(1);
        }
        if (!string.contains(".")) {
            string = string + ".";
        }
        boolean delimiterMatched = false;
        char delimiter = '.';
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!(c >= '0' && c <= '9' || c == delimiter)) {
                // contains not number
                return false;
            }
            if (c == delimiter) {
                // delimiter matched twice
                if (delimiterMatched) {
                    return false;
                }
                delimiterMatched = true;
            }
        }
        // if matched delimiter once return true
        return delimiterMatched;
    }

    public static String mark(String str) {
        String str2 = str.replaceAll("\\\\\"", "cc");
        String marked = "";
        boolean isInString = false;
        for (int i = 0; i < str2.length(); i++) {
            if (str2.charAt(i) == '\"') {
                isInString = !isInString;
            }
            if (isInString) {
                marked += '\"';
            } else {
                marked += str2.charAt(i);
            }
        }
        return marked;
    }

    public static String takingAnd(String str) {
        str = str.substring(1, str.length() - 1);
        str = str.replaceAll("\\\\\"", "cc");
        str = '"' + str + '"';
        String res = "";
        String marked = mark(str);
        boolean isInString = false;
        for (int i = 0; i < str.length(); i++) {
            if (marked.charAt(i) == '\"') {
                isInString = !isInString;
            }
            if (str.charAt(i) == marked.charAt(i)) {
                res += str.charAt(i);
            } else {
                if (str.charAt(i) == '\\') {
                    res += '\\';
                } else if (str.charAt(i) == '<') {
                    res += '<';
                } else if (str.charAt(i) == '>') {
                    res += '>';
                } else {
                    res += '0';
                }
            }
        }
        return res;
    }

    public static String cleanUp(String str) {
        String marked = mark(str);
        for (int i = 0; i < str.length(); i++) {
            if (marked.charAt(i) == ' ') {
                str = str.substring(0, i) + str.substring(i + 1);
                marked = marked.substring(0, i) + marked.substring(i + 1);
                i--;
            }
        }
        return str;
    }

    public void handleInsideQs(String str) {
        str = cleanUp(str);
        String anded = takingAnd(str);
        if (anded.contains("\"\"")) {
            this.isValid = false;
            return;
        }
        anded = anded.replace("\\\\", "bb");
        anded = anded.replace("\\<", "bb");
        anded = anded.replace("\\>", "bb");
        if (anded.contains("\\")) {
            this.isValid = false;
            return;
        }
        if (anded.contains("<")) {
            this.isValid = false;
            return;
        }
        if (anded.contains(">")) {
            this.isValid = false;
            return;
        }
    }

    public static int findPair(String str, String open, String close) {// <...> </...>
        int openCount = 0;
        int closeCount = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.startsWith(open, i)) {
                openCount++;
            } else if (str.startsWith(close, i)) {
                closeCount++;
                if (openCount == closeCount) {
                    return i;
                }
            }
        }
        return -1;
    }
}

class XMLObject extends XMLValue {//<a><b>"ali"</b><c><d>"sara"</d><b><e>"hamid"</e></b></c></a>  watch out
    public HashMap<String, ArrayList<String>> values;

    XMLObject(String str) {//str = <a>123</a><a>1.23</a><a>.123</a><b>-.123</b><b>-9.123</b><b>-123</b>
        str = cleanUp(str);
        values = new HashMap<>();
        if (str.isEmpty()) {
            size = 1;
            return;
        }
        while (!str.isEmpty()) {
            if (mark(str).charAt(0) != '<') {// it could be " or 235 or true or false or nothing
                if (mark(str).charAt(0) == '"') {
                    size = 1;
                    handleInsideQs(str);
                    return;
                } else if (isReal(str)) {
                    size = 1;
                    return;
                } else if (str.equals("true")) {
                    size = 1;
                    return;
                } else if (str.equals("false")) {
                    size = 1;
                    return;
                } else {
                    isValid = false;
                    return;
                }
            }
            int indexOfEndOfFirstOpenKey = mark(str).indexOf('>');
            if (indexOfEndOfFirstOpenKey == -1) {
                isValid = false;
                return;
            }
            String firstKey = str.substring(1, indexOfEndOfFirstOpenKey); /// without <>
            String firstCloseKey = "</" + firstKey + ">";
            firstKey = "<" + firstKey + ">"; /// with <>
            if (!str.contains(firstCloseKey)) {
                isValid = false;
                return;
            }
            int indexOfTheStartOfCloseKey = findPair(mark(str), firstKey, firstCloseKey);
            if (indexOfTheStartOfCloseKey == -1) {
                isValid = false;
                return;
            }/// isValid test
            ArrayList<String> valueList;
            if (values.containsKey(firstKey)) {
                valueList = values.get(firstKey);
            } else {
                valueList = new ArrayList<>();
            }
            String firstValue = str.substring(indexOfEndOfFirstOpenKey + 1, indexOfTheStartOfCloseKey);
            valueList.add(firstValue);
            values.put(firstKey, valueList);
            if (indexOfEndOfFirstOpenKey == indexOfTheStartOfCloseKey) {
                str = "";
            } else {
                str = str.substring(indexOfTheStartOfCloseKey + firstCloseKey.length());
            }
        }
        size = values.size();
    }
}

class TreeNode {
    public String name;
    public String value;
    public ArrayList<TreeNode> children;
    public ArrayList<String> namesOfTheChildren = new ArrayList<>();
    public HashMap<String, TreeNode> nameToChild = new HashMap<>();

    public TreeNode(String name, String value) {
        this.name = name;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
        for (TreeNode node : children) {
            nameToChild.put(node.name, node);
        }
        namesOfTheChildren = new ArrayList<>(nameToChild.keySet());
        namesOfTheChildren.sort(String::compareTo);
        children.clear();
        for (String name : namesOfTheChildren) {
            children.add(nameToChild.get(name));
        }

    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public int size() {
        int totalSize = 1;
        if (namesOfTheChildren.isEmpty()) {
            return totalSize;
        }
        for (TreeNode child : children) {
            totalSize += child.size();
        }
        return totalSize;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean haveEqualNames(TreeNode other) {
        return name.equals(other.name);
    }

    public boolean haveEqualValues(TreeNode other) {
        return value.equals(other.value);
    }

    public boolean haveEqualChildren(TreeNode other) {
        return children.equals(other.children);
    }

    public boolean areEqual(TreeNode other) {
        return name.equals(other.name) && children.equals(other.children);
    }
}

public class difference extends JSONValue {

    public static int computeTheDifference(TreeNode first, TreeNode latter) {
        int count = 0;
        if (!first.name.equals(latter.name)) {
            count++;
        }
        if (first.nameToChild.size() == latter.nameToChild.size()) {
            if (first.nameToChild.isEmpty()) {
                if (first.value.charAt(0) == '"' && latter.value.charAt(0) == '"') {
                    if (first.value.equals(latter.value)) {
                        return count;
                    } else {
                        return count + 1;
                    }
                } else if (first.value.charAt(0) != '"' && latter.value.charAt(0) != '"') {
                    if (first.value.equals(latter.value)) {
                        return count;
                    } else {
                        return count + 1;
                    }
                } else {
                    return count + 2;
                }
            }
        }
        for (String key : first.nameToChild.keySet()) {
            if ((first.nameToChild.get(key).value.equals("{}") || first.nameToChild.get(key).value.equals("<")) && latter.nameToChild.get(key).value != first.nameToChild.get(key).value) {
                count++;
            }
            if (latter.nameToChild.containsKey(key)) {
                count += computeTheDifference(first.nameToChild.get(key), latter.nameToChild.get(key));
            } else {
                if (Objects.equals(key, first.namesOfTheChildren.get(first.namesOfTheChildren.size() - 1))) {
                    count++;
                    count += first.nameToChild.get(key).size();
                } else {
                    count++;
                    if (!latter.namesOfTheChildren.isEmpty()) {
                        String kk = latter.namesOfTheChildren.get(latter.namesOfTheChildren.size() - 1);
                        count += latter.nameToChild.get(kk).size();
                    }
                    count++;
                    count += first.nameToChild.get(key).size();
                }
            }
        }
        return count;
    }

    public static JSONObject arrStringToJSON(String arrString) {
        arrString = cleanUp(arrString);
        JSONArray JA = new JSONArray(arrString);
        ArrayList<String> tempArray = JA.values;
        String JOString = "{";
        for (int i = 0; i < tempArray.size() - 1; i++) {
            String indexString = "\"" + (char) (i + '0') + "\"";
            JOString += indexString + ":" + tempArray.get(i) + ",";
        }
        int i = tempArray.size() - 1;
        String indexString = "\"" + (char) (i + '0') + "\"";
        JOString += indexString + ":" + tempArray.get(i);
        JOString += "}";
        return new JSONObject(JOString);
    }

    public static TreeNode jsonToTree(String rootName, JSONObject JObject) {
        TreeNode root = new TreeNode(rootName, "{}");
        for (String key : JObject.values.keySet()) {
            String value = JObject.values.get(key);
            if (value.charAt(0) == '{') {
                JSONObject JO = new JSONObject(value);
                TreeNode childNode = jsonToTree(key.substring(1,key.length()-1), JO);
                root.addChild(childNode);
            } else if (value.charAt(0) == '[') {
                JSONObject JO = arrStringToJSON(value);
                TreeNode childNode = jsonToTree(key.substring(1,key.length()-1), JO);
                root.addChild(childNode);
            } else {
                TreeNode childNode = new TreeNode(key.substring(1, key.length() - 1), value);
                root.addChild(childNode);
            }
        }
        return root;
    }

    public static TreeNode xmlToTree(XMLObject xmlObject, String rootName) {
        TreeNode root = new TreeNode(rootName, "<>");
        for (String key : xmlObject.values.keySet()) {
            ArrayList<String> valueList = xmlObject.values.get(key);
            if (valueList.size() == 1) {
                String value = valueList.get(0);
                if (value.isEmpty()) {
                    value = "null";
                }
                if (value.startsWith("<")) {
                    XMLObject nestedObject = new XMLObject(value);
                    TreeNode childNode = xmlToTree(nestedObject, key);
                    root.addChild(childNode);
                } else {
                    TreeNode leafNode = new TreeNode(key.substring(1, key.length() - 1), value);
                    root.addChild(leafNode);
                }
            } else {
                TreeNode arrayNode = new TreeNode(key.substring(1, key.length() - 1), "[]");
                for (int i = 0; i < valueList.size(); i++) {
                    String value = valueList.get(i);
                    if (value.isEmpty()) {
                        value = "null";
                    }
                    if (value.startsWith("<")) {
                        XMLObject nestedObject = new XMLObject(value);
                        TreeNode nestedNode = xmlToTree(nestedObject, (char) (i + '0') + "");
                        arrayNode.addChild(nestedNode);
                    } else {
                        TreeNode leafNode = new TreeNode((char) (i + '0') + "", value);
                        arrayNode.addChild(leafNode);
                    }
                }
                root.addChild(arrayNode);
            }
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode a = jsonToTree("root", new JSONObject("{\"a\":null,\"b\":[[\"second\",\"first\"]]}"));
        Scanner scanner = new Scanner(System.in);
        StringBuilder part1 = new StringBuilder();
        StringBuilder part2 = new StringBuilder();
        boolean firstPart = true;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("\n") || line.isEmpty()) {
                break;
            }
            if (line.equals("---")) {
                firstPart = false;
                continue;
            }
            if (firstPart) {
                part1.append(line);
            } else {
                part2.append(line);
            }
        }
        String str1 = part1.toString();
        String str2 = part2.toString();
        TreeNode first;
        TreeNode latter;
        int r = 0;
        if (str1.charAt(0) == '{') {
            if (str1.contains("[")) {
                if(str1.substring(str1.indexOf("[")).contains(",")) {
                    r = -1;
                }
            }
            first = jsonToTree("root", new JSONObject(str1));
        } else {
            int endOfMainKey = mark(str1).indexOf('>');
            str1 = str1.substring(endOfMainKey + 1, str1.length() - 2 - endOfMainKey);
            first = xmlToTree(new XMLObject(str1), "root");
        }
        if (str2.charAt(0) == '{') {
            latter = jsonToTree("root", new JSONObject(str2));
        } else {
            int endOfMainKey = mark(str2).indexOf('>');
            str2 = str2.substring(endOfMainKey + 1, str2.length() - 2 - endOfMainKey);
            latter = xmlToTree(new XMLObject(str2), "root");
        }
        System.out.println(computeTheDifference(first, latter) + r);
    }
}
