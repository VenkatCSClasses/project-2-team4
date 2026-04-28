[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/2HAsn8j_)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23397187)

The product is a local epub reader. There is a server and a database that holds all the users currently registered. A user account has a username, password, and a personal library of books that is initialized at account creation and can be added to/removed from. From the main page, a user can access their own personal library or search for a book. Searching is done by pasing a CSV file obtained from Project Gutenberg that lists all of its books as of 2026. The user can search by author or title, or get a generalized view of books from certain genres. After a book is selected from the search, the app displays some of the data about the book: tite, author(s), Gutenberg ID, genre(s), and language(s). The user can then either read the book directly, add it to their library, or go back to the search. When a book is read, either from their library or from a search result, a web request is made to Project Gutenberg's site using the Gutenberg ID from the CSV file. If the request is a success, the html page of the book is displayed. If there is an error, the user is prompted to try again or return to the previous page.

Use Case Diagram:
<img width="914" height="2935" alt="image" src="https://github.com/user-attachments/assets/fbb289d4-9278-422b-bb94-795c8fb41d56" />

Sequence Diagram 1:
[![](https://mermaid.ink/img/pako:eNplkclqwzAQhl9FzNkJtRN50SEQUuipUBLaQ_FFsccLsSV3JEHTkHevbLehi06a5fvnH-kChS4RBBh8c6gKvG9lTbLPFfPn2SAtNps9yhJpOwyCHVBS0bAcKpLqhMpYbFUOrFU-91U8ntnW2UZTDrPMjfdaD86iOiLVu8OLYE-SDHpSTv3GCxW6c71ilaZ_Q2axnwKL3-b2aB0p1rXGMl0x2XXsqPXJ_HXhqXGzG2D1wPjcyo7SYMm0YmZaBgKoqS1BWHIYQI_UyzGEyyiag22wxxyEv5aSTqPJq2cGqV617r8x0q5uQFSyMz5yQynt90PfsoTK29tppyyIkPN4UgFxgXcQnC_DdcbTKA5jnvIkCuDsu6J4uUp5lCVJEnFfjq8BfExz75ZZFIVp5IFsvUrDJAwAy9Zqepz_e_r26yehRaSj?type=png)](https://mermaid.live/edit#pako:eNplkclqwzAQhl9FzNkJtRN50SEQUuipUBLaQ_FFsccLsSV3JEHTkHevbLehi06a5fvnH-kChS4RBBh8c6gKvG9lTbLPFfPn2SAtNps9yhJpOwyCHVBS0bAcKpLqhMpYbFUOrFU-91U8ntnW2UZTDrPMjfdaD86iOiLVu8OLYE-SDHpSTv3GCxW6c71ilaZ_Q2axnwKL3-b2aB0p1rXGMl0x2XXsqPXJ_HXhqXGzG2D1wPjcyo7SYMm0YmZaBgKoqS1BWHIYQI_UyzGEyyiag22wxxyEv5aSTqPJq2cGqV617r8x0q5uQFSyMz5yQynt90PfsoTK29tppyyIkPN4UgFxgXcQnC_DdcbTKA5jnvIkCuDsu6J4uUp5lCVJEnFfjq8BfExz75ZZFIVp5IFsvUrDJAwAy9Zqepz_e_r26yehRaSj)

Sequence Diagram 2:
https://mermaid.live/edit#pako:eNqNU8tu2zAQ_BViTwkgy3o4asRDDkmKtEACFI3bAIUuFLmRCUukSlJtXcP_XlJy7LTuIbpIy52ZHQ24W-BaIFCw-H1AxfFWssawrlLEP4w7bcgXi2aqe2ac5LJnypFH9gPFtdZre9p7wvqrxJ-njbvBoarRNJWamkF7dnV1FKOEt5KvLangMzJRAdGKMFL73sQ4Qv_lGWQOAwnNI0eFZ9axBiPSvAz9KKJRaCldi-f_kdv7pqTVTJxVcGDG2jRzzvgK59gP9XwrxW7eN-E1k52fYuOV69oK9qp7IS95-GNKPiyXn8jd-yUxIWvrJugBMHttwKAbjLKe83A_eiZcKw90f-t7SkiQEiFt37KNnbBSeYGQw1tivmZ8TZwm97I2zGwqeFPOx5Mp69M4T73Z0BwdWtJK6yCCxkgB1JkBI-jQdCyUsA1iFbgVdlgB9Z-CmXVwtvMcf5O-ad290IwemhXQZ9ZaXw298O72t_gAQeXDuNGDckDLUQHoFn4BzdMyfpdki8vsokyLYhHBBmiaZnGa51mWF0WRpHmS7iL4PY5M4sv0Is_KRZklZZbmWREBCunX5GHao3Gddn8AR0Ecpg

Sequence Diagram 3:
![alt text](Save_book_sequence.png)


Product Backlog:
https://docs.google.com/document/d/1S9wM5b76LGXwzVOFlHN9EMhisN919FD_ku9UxcKVPKM/edit?usp=sharing 

Scrum Diary:
https://docs.google.com/document/d/1ojr-jXbMHN2DDmy5UyXlCafpM6ZRrjb3gz6IDgujaJE/edit?tab=t.0

State Diagram:
https://mermaid.ai/d/014ce42a-4f2d-4a33-8216-adc4fb94a789
