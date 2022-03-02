import os
import string
import math

def getVocabulary(m,n,all_words_flag):
    file_content = open("aclImdb/imdb.vocab", "r",encoding="utf8")
    words_array = file_content.read().split("\n")
    vocabulary = dict()
    if all_words_flag:
        m = len(words_array)

    value = 0
    j = 0
    for i in range(m):
        if n > j:
            j += 1
            continue
        else:
            vocabulary[words_array[i]] = value
            value += 1
    return vocabulary

def contain(text,word):
    # if word in string.punctuation:
    #     if word in text:
    #         return True
    #     else:
    #         return False
    if (word + " " in text) or  (" " + word + " " in text) or  (" "+word in text):
        return True
    return False