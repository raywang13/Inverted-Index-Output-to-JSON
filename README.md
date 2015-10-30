# Inverted-Index-Output-to-JSON
Outputs an inverted index into JSON format

Wrote a Java program that recursively processes all text files in a directory 
and builds an inverted index to store the mapping from words to the documents (and position within those documents) 
where those words were found. For example, suppose we have the following mapping stored in our inverted index:

```
{
  "capybara": {
    "mammals.txt": [
      11
    ]
  },
  "platypus": {
    "mammals.txt": [
      3,
      8
    ],
    "venomous.txt": [
      2
    ]
  }
}
```

This indicates that the word `capybara` is found in the file `mammals.txt` in position 11. The word `platypus` 
is found in two files, `mammals.txt` and `venomous.txt`. In the file `mammals.txt`, the word `platypus` appears 
twice in positions 3 and 8. In file `venomous.txt`, the word `platypus` is in position 2 in the file.

Core functionality:

- Created an custom **inverted index** data structure that stores a mapping from a word to the file(s) the word was found, 
and the position(s) in that file the word is located using multiple nested data structures.

- Stored the normalized relative path for file locations as a `String` object. Do not convert the file paths to absolute paths!

- Positions stored in the inverted index would start at 1. For example, if a file has words "ant bat cat", then "ant" 
  is in position 1, "bat" is in position 2, and "cat" is in position 3.

- Traversed a directory and its subdirectories and parsed all of the text files found. Any file ending in the `.txt` 
extension (case-insensitive) would be considered a text file.

- Replaced all characters except letters and digits with an empty string. This includes the `_` underscore character as well. 
For example, the word `age_long` would be seen as `agelong` since the `_` underscore will be replaced with an empty string.

- Separated text into words by any whitespace character, including spaces, tabs, and newline characters.

- Words are case-insensitive. For example, the words APPLE, Apple, apple, and aPpLe should all be seen as the same word.

- Process command-line parameters to determine the directory to parse and output to produce.

- Output the inverted index to a text file if the proper command-line parameters are provided.
