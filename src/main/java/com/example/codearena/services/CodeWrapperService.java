package com.example.codearena.services;

import org.springframework.stereotype.Service;

@Service
public class CodeWrapperService {

    public String wrapJava(String userCode) {

        // 1. Scrub invisible characters and convert to static class
        String modifiedUserCode = userCode.replace("\u00A0", " ") // 🔥 Kills invisible non-breaking spaces
                .replace("public class Solution", "static class Solution")
                .replace("class Solution", "static class Solution");

        // 2. Build the string manually to ensure NO leading spaces hide the public class
        StringBuilder sb = new StringBuilder();
        sb.append("import java.util.*;\n");
        sb.append("\n");
        sb.append("public class MyClass {\n"); // 🔥 Pushed to the absolute left edge!
        sb.append("    public static void main(String[] args) {\n");
        sb.append("        try {\n");
        sb.append("            Scanner sc = new Scanner(System.in);\n");
        sb.append("            if (!sc.hasNextLine()) {\n");
        sb.append("                System.out.print(\"Execution Error: No input provided\");\n");
        sb.append("                return;\n");
        sb.append("            }\n");
        sb.append("            String arrStr = sc.nextLine().trim();\n");
        sb.append("            arrStr = arrStr.replace(\"[\", \"\").replace(\"]\", \"\").replace(\" \", \"\");\n");
        sb.append("            String[] parts = arrStr.split(\",\");\n");
        sb.append("            int[] nums = new int[parts.length];\n");
        sb.append("            for(int i=0; i<parts.length; i++) {\n");
        sb.append("                if(!parts[i].isEmpty()) nums[i] = Integer.parseInt(parts[i]);\n");
        sb.append("            }\n");
        sb.append("            int target = sc.nextInt();\n");
        sb.append("            Solution sol = new Solution();\n");
        sb.append("            int[] res = sol.twoSum(nums, target);\n");
        sb.append("            System.out.print(\"[\" + res[0] + \",\" + res[1] + \"]\");\n");
        sb.append("        } catch (Exception e) {\n");
        sb.append("            System.out.print(\"Execution Error: \" + e.getMessage());\n");
        sb.append("        }\n");
        sb.append("    }\n");
        sb.append("    \n");
        sb.append("    ").append(modifiedUserCode).append("\n"); // Inject user code
        sb.append("}\n");

        return sb.toString();
    }
    public String wrapPython(String userCode) {
        StringBuilder sb = new StringBuilder();

        // 1. Inject the user's algorithm first
        sb.append("import sys\n\n");
        sb.append(userCode).append("\n\n");

        // 2. Append the hidden driver code to read the test case and call the algorithm
        sb.append("if __name__ == '__main__':\n");
        sb.append("    try:\n");
        sb.append("        input_data = sys.stdin.read().split()\n");
        sb.append("        if len(input_data) >= 2:\n");
        sb.append("            # Parse the array\n");
        sb.append("            arr_str = input_data[0].replace('[', '').replace(']', '').replace(' ', '')\n");
        sb.append("            nums = [int(x) for x in arr_str.split(',') if x]\n");
        sb.append("            # Parse the target\n");
        sb.append("            target = int(input_data[1])\n");
        sb.append("            \n");
        sb.append("            # Execute user code\n");
        sb.append("            sol = Solution()\n");
        sb.append("            res = sol.twoSum(nums, target)\n");
        sb.append("            \n");
        sb.append("            # Print exactly how the database expects: [x,y]\n");
        sb.append("            print(f'[{res[0]},{res[1]}]', end='')\n");
        sb.append("    except Exception as e:\n");
        sb.append("        print('Execution Error:', e)\n");

        return sb.toString();
    }
    public String wrapCpp(String userCode) {
        StringBuilder sb = new StringBuilder();

        // 1. Inject standard headers and the user's algorithm
        sb.append("#include <iostream>\n");
        sb.append("#include <vector>\n");
        sb.append("#include <string>\n");
        sb.append("using namespace std;\n\n");
        sb.append(userCode).append("\n\n");

        // 2. Build the hidden main driver
        sb.append("int main() {\n");
        sb.append("    string arrStr;\n");
        sb.append("    if (!(cin >> arrStr)) return 0;\n");

        // Parse the "[2,7,11,15]" string into a vector<int>
        sb.append("    vector<int> nums;\n");
        sb.append("    string temp = \"\";\n");
        sb.append("    for(char c : arrStr) {\n");
        sb.append("        if(isdigit(c) || c == '-') {\n");
        sb.append("            temp += c;\n");
        sb.append("        } else if(c == ',' || c == ']') {\n");
        sb.append("            if(!temp.empty()) {\n");
        sb.append("                nums.push_back(stoi(temp));\n");
        sb.append("                temp = \"\";\n");
        sb.append("            }\n");
        sb.append("        }\n");
        sb.append("    }\n");

        // Parse the target
        sb.append("    int target;\n");
        sb.append("    cin >> target;\n");

        // Execute user code
        sb.append("    Solution sol;\n");
        sb.append("    vector<int> res = sol.twoSum(nums, target);\n");

        // Print exactly how the database expects: [x,y]
        sb.append("    if(res.size() >= 2) cout << \"[\" << res[0] << \",\" << res[1] << \"]\";\n");
        sb.append("    else cout << \"[]\";\n");

        sb.append("    return 0;\n");
        sb.append("}\n");

        return sb.toString();
    }
}