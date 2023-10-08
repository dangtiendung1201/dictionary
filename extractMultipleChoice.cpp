#include <bits/stdc++.h>

using namespace std;

string s;

int main()
{
    freopen("MultipleChoice.txt", "r", stdin);
    freopen("MultipleChoiceData.txt", "w", stdout);

    while (getline(cin, s))
    {
        int cnt = 0;
        string englishWord = "";
        string example = "";

        for (int i = 0; i < s.size(); i++)
        {
            // if s[i] is quotation mark
            if (s[i] == '\"')
            {
                cnt++;
                continue;
            }

            if (cnt == 1)
            {
                englishWord += s[i];
            }
            if (cnt == 5)
            {
                example += s[i];
            }
        }

        cout << englishWord << '\t' << example << endl;
    }
}