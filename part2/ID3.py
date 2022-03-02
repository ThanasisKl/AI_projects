import os
import string
import math
import matplotlib.pyplot as plt
import copy
from Node import Node
from math import log1p
from functions import getVocabulary
from functions import contain



class ID3Generator:
    def create(self,positives,negatives,dictionary):
        node = Node(positives,negatives)
        if len(dictionary) == 0:
            if len(positives) > len(negatives):
                node.result = -1
                return node
            else:
                node.result = 1
                return node
        elif len(positives) == 0 and len(negatives) == 0:
            node.result = -1
            return node
        elif len(positives) == 0:
            node.result = 1
            return node
        elif len(negatives) == 0:
            node.result = -1
            return node
        elif (len(positives)/(len(positives)+len(negatives)) > 0.8):
            node.result = -1
            return node
        elif (len(negatives)/(len(positives)+len(negatives)) > 0.8):
            node.result = 1
            return node

        Hc = -(len(positives)/(len(positives)+len(negatives)))*log1p(len(positives)/(len(positives)+len(negatives)))-(len(negatives)/(len(positives)+len(negatives)))*log1p((len(negatives)/(len(positives)+len(negatives))))

        positives_found=[]
        positives_not_found = []
        negatives_found=[]
        negatives_not_found = []
        max_ig = 0
        best_word = list(dictionary.keys())[0]
        for word in dictionary:
            sumP = 0
            sumP_2 = 0
            sumN = 0
            sumN_2 = 0
            for text in positives:
                if text[dictionary[word]] == 1:
                    sumP += 1
                else:
                    sumP_2 += 1
            for text in negatives:
                if text[dictionary[word]] == 1:
                    sumN += 1
                else:
                    sumN_2 += 1
            sum_of_texts = len(positives) + len(negatives)
            if sumP_2 == 0 and sumN_2 == 0:
                S_zero = 0
                S_one = ((sumP+sumN)/sum_of_texts)*(-(sumP/(sumP+sumN)*log1p(sumP/(sumP+sumN))) - (sumN/(sumP+sumN)*log1p(sumN/(sumP+sumN))))
            elif sumP == 0 and sumN == 0:
                S_one = 0
                S_zero = ((sumP_2+sumN_2)/sum_of_texts)*(-(sumP_2/(sumP_2+sumN_2)*log1p(sumP_2/(sumP_2+sumN_2))) - (sumN_2/(sumP_2+sumN_2)*log1p(sumN_2/(sumP_2+sumN_2))))
            else:
                S_zero = ((sumP_2+sumN_2)/sum_of_texts)*(-(sumP_2/(sumP_2+sumN_2)*log1p(sumP_2/(sumP_2+sumN_2))) - (sumN_2/(sumP_2+sumN_2)*log1p(sumN_2/(sumP_2+sumN_2))))
                S_one = ((sumP+sumN)/sum_of_texts)*(-(sumP/(sumP+sumN)*log1p(sumP/(sumP+sumN))) - (sumN/(sumP+sumN)*log1p(sumN/(sumP+sumN))))
            ig = Hc - (S_zero + S_one)
            if ig > max_ig:
                max_ig = ig
                best_word = word

        for text in positives:
            if text[dictionary[best_word]] == 1:
                positives_found.append(text)
            else:
                positives_not_found.append(text)
        for text in negatives:
            if text[dictionary[best_word]] == 1:
                negatives_found.append(text)
            else:
                negatives_not_found.append(text)

        node.word = best_word
        new_dictionary = dict()
        dictionary.pop(best_word)
        for word in dictionary:
            new_dictionary[word] = dictionary[word]

        node.exist = self.create(positives_found,negatives_found, new_dictionary)
        node.not_exist = self.create(positives_not_found,negatives_not_found,new_dictionary)
        return node

try_passed = False
all_flag = False
testFor_flag = True
m = ""
try:
    m = input("Give m (type all to use all the vocabulary): ")
    m = int(m)
    n = input("Give n: ")
    n = int(n)
    if m > 0 and n >=0:
        try_passed = True
except ValueError:
  if m == "all":
      all_flag = True
      n = input("Give n: ")
      n = int(n)
      if n >= 0:
        try_passed = True

if not try_passed:
    print("Please give a numbers as input greater than zero")
else:

        print("Training...")
        vocabulary = getVocabulary(m,n, all_flag)

        texts_positives = []
        files_names_list = os.listdir("aclImdb/train/pos")
        list_length = len(files_names_list)
        sum = 0
        full_flag = False
        for file in files_names_list:
            file_content = open("aclImdb/train/pos/"+file, "r",encoding="utf8").read()
            vec = []
            for word in vocabulary:
                if contain(file_content,word):
                    vec.append(1)
                else:
                    vec.append(0)
            texts_positives.append(vec)
            sum += 1
            print("Reading Positive Comments: "+str(int((sum/list_length)*100)) + '%',end ="\r")

        print()
        texts_negatives = []
        files_names_list = os.listdir("aclImdb/train/neg")
        list_length = len(files_names_list)
        sum = 0
        for file in files_names_list:
            file_content = open("aclImdb/train/neg/"+file, "r",encoding="utf8").read()
            vec =  []
            for word in vocabulary:
                if contain(file_content,word):
                   vec.append(1)
                else:
                    vec.append(0)
            texts_negatives.append(vec)
            sum += 1
            print("Reading Negative Comments: "+str(int((sum/list_length)*100)) + '%',end ="\r")
        print()
        tree = ID3Generator()
        root = tree.create(texts_positives,texts_negatives,vocabulary)

        print("Testing positive data...")
        files_names_list = os.listdir("aclImdb/train/pos")
        list_length = len(files_names_list)
        number_of_negatives = 0
        number_of_positives = 0
        for file in files_names_list:
            node = root
            result = None
            file_content = open("aclImdb/train/pos/"+file, "r",encoding="utf8").read().lower()
            while node.word != None:
                if contain(file_content,node.word):
                    node = node.exist
                else:
                    node = node.not_exist
            result = node.result
            if result == 1 :
                number_of_negatives += 1
            elif result == -1:
                number_of_positives += 1

        print("Success rate in positives: "+str(int((number_of_positives/(number_of_positives+number_of_negatives)*100))) + '%')

        print("Testing negative data...")
        files_names_list = os.listdir("aclImdb/train/neg")
        list_length = len(files_names_list)
        number_of_negatives = 0
        number_of_positives = 0
        for file in files_names_list:
            node = root
            result = None
            file_content = open("aclImdb/train/neg/"+file, "r",encoding="utf8").read().lower()
            while node.word != None:
                if contain(file_content,node.word):
                    node = node.exist
                else:
                    node = node.not_exist
            result = node.result
            if result == 1 :
                number_of_negatives += 1
            elif result == -1:
                number_of_positives += 1

        print("Success rate in negatives: "+str(int((number_of_negatives/(number_of_positives+number_of_negatives)*100))) + '%')









