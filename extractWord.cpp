#include<bits/stdc++.h>

using namespace std;

int cnt;
string s;
string word;

int main()
{
    freopen("input.txt", "r", stdin);
    freopen("unsortedWord.txt", "w", stdout);

    while(getline(cin, s))
    {
        if (s[0] == '@')
        {
            for (int i = 0; i < s.size(); i++)
            {
                if (s[i] == '/')
                {
                    string tmp = s.substr(1, i - 2);
                    cout << tmp << endl;
                    cnt++;
                    break;
                }
                else if (i == s.size() - 1)
                {
                    string tmp = s.substr(1, i);
                    cout << tmp << endl;
                    cnt++;
                    break;
                }
            } 
        }
    }

    cout << cnt << endl;

    // while(getline(cin, s))
    // {
    //     if (s[0] == '@')
    //     {
    //         // extract right after @ to before " /"
    //         for (int i = 0; i < s.size(); i++)
    //         {
    //             if (s[i] == '/')
    //             {
    //                 string tmp = s.substr(1, i - 1);
    //                 cout << tmp << endl;
    //                 break;
    //             }
    //         }
    //     }
    // }
}