import os
import string
import math
import matplotlib.pyplot as plt
from functions import getVocabulary
from functions import contain


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
      try_passed = True
      all_flag = True

try:
    testFor = input("Test results for train positive data (1), for train negative data (2), for test positive data (3) or for test negative data (4): ")
    testFor = int(testFor)
except ValueError:
    testFor_flag = False

if testFor_flag:
    if not testFor-1 in range(4):
        testFor_flag = False
if not try_passed:
    print("Please give a numbers as input greater than zero")
elif not testFor_flag:
    print("Please give as input values 1,2,3,4")
elif testFor_flag:
        vocabulary = getVocabulary(m,n, all_flag)
        print()
        print("Training...")

        texts_positives = []
        files_names_list = os.listdir("aclImdb/train/pos")
        list_length = len(files_names_list)
        sum = 0
        for file in files_names_list:
            file_content = open("aclImdb/train/pos/"+file, "r",encoding="utf8").read().lower()
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
            file_content = open("aclImdb/train/neg/"+file, "r",encoding="utf8").read().lower()
            vec =  []
            for word in vocabulary:
                if contain(file_content,word):
                   vec.append(1)
                else:
                    vec.append(0)
            texts_negatives.append(vec)
            sum += 1
            print("Reading Negative Comments: "+str(int((sum/list_length)*100)) + '%',end ="\r")
        
        positives_flag = False
        path = ""
        if testFor == 1:
            path =  "aclImdb/train/pos"
            positives_flag = True
        elif testFor == 2:
            path = "aclImdb/train/neg"
        elif testFor == 3:
            path = "aclImdb/test/pos"
            positives_flag = True
        else:
            path = "aclImdb/test/neg"


        print()
        print("Testing...")
        files_names_list = os.listdir(path)
        p_counter = n_counter = e_counter = 0
        sum = 0
        list_length = len(files_names_list)
        positive_texts_number = len(texts_positives)
        negative_texts_number = len(texts_negatives)
        correct_on_test_set = 0
        counter = 0
        for file in files_names_list:
            counter +=1
            file_content = open(path + '/' + file, "r",encoding="utf8").read().lower()
            Ppos = Pneg = 0
            for key in vocabulary:
                indexInVoc = vocabulary[key]
                counter1 = counter2 =  0
                if contain(file_content,key):
                    for x in texts_positives:
                        if x[indexInVoc] == 1:
                            counter1 += 1
                    for x in texts_negatives:
                        if x[indexInVoc] == 1:
                            counter2 += 1
                else:
                    for x in texts_positives:
                        if x[indexInVoc] == 0:
                            counter1 += 1
                    for x in texts_negatives:
                        if x[indexInVoc] == 0:
                            counter2 += 1

                Ppos += math.log((counter1+1)/(positive_texts_number+2),2)
                Pneg += math.log((counter2+1)/(negative_texts_number+2),2)

            allFiles = positive_texts_number+negative_texts_number
            probPositive = positive_texts_number/allFiles
            probNegative = negative_texts_number/allFiles
            if probPositive*Ppos > probNegative*Pneg :
                p_counter += 1
            else:
                n_counter += 1
            sum += 1
            if sum == 100:
                correct_on_test_set += (p_counter/(p_counter+n_counter))
                break

            if positives_flag:
                print("Positives: "+str(p_counter)," Negatives: " + str(n_counter)," Success rate: " + str(int((p_counter/(p_counter+n_counter))*100)) +"% "," ("+str(int((sum/list_length)*100)) +"%)",end ="\r")
            else:
                print("Positives: "+str(p_counter)," Negatives: " + str(n_counter)," Success rate: " + str(int((n_counter/(p_counter+n_counter))*100)) +"% "," ("+str(int((sum/list_length)*100)) +"%)",end ="\r")
        print()
                    

