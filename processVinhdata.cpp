#include<bits/stdc++.h>

using namespace std;

string s;
string englishWord;
string content;
string ipa;
string type;
string meaning;
string example;

string extractIPA(string content)
{
    string res = "";

    // find first "<br />"
    int lastPos = content.find("<br />");
    int firstPos = content.find("/");

    // extract res = content firstPos -> lastPos
    for (int i = firstPos; i < lastPos; i++)
    {
        res += content[i];
    }

    if (res == "")
        return "NULL";

    return res;
}

string extractType(string content)
{
    string res = "";

    // firstPos find "<br />*"
    int firstPos = content.find("<br />*");
    // lastPos find "<br />-"
    int lastPos = content.find("<br />-");

    // if not found
    if (firstPos == -1 || lastPos == -1)
        return "danh từ";

    for (int i = firstPos + 7; i < lastPos; i++)
    {
        res += content[i];
    }

    if (res == "")
        return "danh từ";

    while (res[0] == ' ')
        res.erase(res.begin());

    return res;
}

string extractMeaning(string content)
{
    string res = "";
    int pos = 0;

    while (content.find("<br />-", pos) != -1)
    {
        int firstPos = content.find("<br />-", pos);
        // lastPos = next "<br />"
        int lastPos = content.find("<br />", firstPos + 7);
        if (lastPos == -1)
            lastPos = content.find("</Q></I>", firstPos + 7);

        // extract res = content firstPos -> lastPos
        string tmp = "";
        for (int i = firstPos + 7; i < lastPos; i++)
        {
            tmp += content[i];
        }
        while (tmp[0] == ' ')
            tmp.erase(tmp.begin());

        res += tmp + " | ";
        pos = lastPos;
    }

    // return res that remove 3 last character
    if (res == "")
        return "NULL";

    return res.substr(0, res.size() - 3);
}

string extractExample(string content)
{
    string res = "";
    int pos = 0;

    while (content.find("<br />=", pos) != -1)
    {
        int firstPos = content.find("<br />=", pos);
        // lastPos = next "<br />"
        int lastPos = content.find("<br />", firstPos + 7);
        if (lastPos == -1)
            lastPos = content.find("</Q></I>", firstPos + 7);

        // extract res = content firstPos -> lastPos
        string tmp = "";
        for (int i = firstPos + 7; i < lastPos; i++)
        {
            tmp += content[i];
        }
        while (tmp[0] == ' ')
            tmp.erase(tmp.begin());

        res += tmp + " | ";
        pos = lastPos;
    }

    // return res that remove 3 last character
    if (res == "")
        return "NULL";

    return res.substr(0, res.size() - 3);
}

int main()
{
    freopen("vinhData.txt", "r", stdin);
    freopen("vinhDataExtract.txt", "w", stdout);

    while (getline(cin, s))
    {
        englishWord = "";
        content = "";
        ipa = "";
        type = "";
        meaning = "";
        int cntQoute = 0;

        for (int i = 0; i < s.size(); i++)
        {
            if (s[i] == '\'') cntQoute++;
            else
            {
                if (cntQoute == 1)
                {
                    englishWord += s[i];
                }
                else if (cntQoute > 1)
                {
                    content += s[i];
                }
            }
        }

        ipa = extractIPA(content);
        type = extractType(content);
        meaning = extractMeaning(content);
        example = extractExample(content);

        cout << englishWord << '\t' << ipa << '\t' << type << '\t' << meaning << '\t' << example << endl;
    }
}