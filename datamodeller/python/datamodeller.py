import numpy as np
import scipy
import sys
from sklearn import linear_model,svm,naive_bayes,neighbors
from sklearn.metrics import confusion_matrix
from sklearn.metrics import precision_recall_fscore_support,classification_report
from sklearn import preprocessing as pp
from sklearn import cross_validation as cv
from sklearn.decomposition import SparsePCA,PCA
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.neighbors import KNeighborsClassifier
import os
from sklearn import svm, grid_search
from sklearn.ensemble import RandomForestClassifier 
from sklearn.cluster import KMeans

def pearsonr(x, y):
  # Assume len(x) == len(y)
  n = len(x)
  sum_x = float(sum(x))
  sum_y = float(sum(y))
  sum_x_sq = sum(map(lambda x: pow(x, 2), x))
  sum_y_sq = sum(map(lambda x: pow(x, 2), y))
  psum = sum(imap(lambda x, y: x * y, x, y))
  num = psum - (sum_x * sum_y/n)
  den = pow((sum_x_sq - pow(sum_x, 2) / n) * (sum_y_sq - pow(sum_y, 2) / n), 0.5)
  if den == 0: return 0
  return num / den

def printPrecRecall(scores):
    appendDataTofile("Class Label        -1           0               1                2")
    appendDataTofile("Precision ",scores[0]) 
    appendDataTofile("Recall    ",scores[1])
    appendDataTofile("F-Score   ",scores[2])
    appendDataTofile("Total Instances ",scores[3])
    appendDataTofile("\n")    
    
def transform(xTrain,yTrain,xTest):
    pca = SparsePCA(n_components=2);
    newXTrain =  pca.fit_transform(xTrain,yTrain)
    newXTest = pca.transform(xTest)
    return newXTrain,newXTest   

def evaluateCorrelationResults(xTrain,yTrain):
    outputFile = open("../files/correlation.csv", 'w+')
    for i in range(1,xTrain.shape[1]):
        correlation = scipy.stats.pearsonr(xTrain[:,i],yTrain)
        outputFile.write("i = "+str(i)+" pearson correlation = "+str(correlation)+"\n")
    outputFile.close()             


def classify(func,xTrain,xTest,yTrain,yTest):        
        clf = func()
        clf.fit(xTrain, yTrain);
        yPred = clf.predict(xTest); 
        resultLR =  clf.score(xTest,yTest);  
        appendDataTofile("accuracy =" , resultLR.mean());              
        appendDataTofile("Confusion matrix",confusion_matrix(yTest, yPred))             
        precScores = precision_recall_fscore_support(yTest, yPred);
        printPrecRecall(precScores)   
        return yPred   

def clusterify(func,xTrain,xTest,yTrain,yTest):        
        clf = func()
        clf.fit(xTrain, yTrain);
        yPred = clf.fit_predict(xTest); 
        return yPred   
    
def gridSearchCVforSVM(xTrain,xTest,yTrain,yTest):
     appendDataTofile("Grid search results")
     svc = svm.SVC()
     #parameters = {'kernel':('linear','rbf'), 'C':[1, 10]}
     parameters = {'kernel':('linear','rbf','poly'), 'C':[1, 50],'gamma':[0.00,10.0]}
     clf = grid_search.GridSearchCV(svc,parameters)
     clf.fit(xTrain, yTrain)
     resultLR =  clf.score(xTest,yTest);  
     appendDataTofile("accuracy =" , resultLR.mean());
     pred =clf.predict(xTest);               
     return pred   
      
    
def appendDataTofile(*text_str):    
    with open(DataModeller.result_file_static, "a") as myfile:
        for i in range(0,len(text_str)):
            myfile.write(str(text_str[i]))    
        myfile.write("\n")
        
class DataModeller:
            
    def __init__(self, training_file, test_file,result_file):
        self.training_file = training_file
        self.test_file = test_file
        DataModeller.result_file_static = result_file
        
        
    def runAnalysis(self):
        
        trainingData = np.loadtxt(open(self.training_file, 'rb'), delimiter = ',', skiprows = 0);
        testData = np.loadtxt(open(self.test_file,'rb'), delimiter = ',', skiprows = 0);
        
        #trainingData = np.genfromtxt(open(self.training_file,'rb'),delimiter=',');
        #testData = np.genfromtxt(open(self.testData,'rb'),delimiter=',');       
        xTrain =  trainingData[:, :trainingData.shape[1]-1]
        yTrain = trainingData[:,trainingData.shape[1]-1]
                  
        xTest = testData[:, :testData.shape[1] -1]
        yTest = testData[:, testData.shape[1]-1]
        
        #evaluateCorrelationResults(xTrain, yTrain)
        
        #xTrain,xTest = transform(xTrain,yTrain,xTest)
        
        #tf-idf transformation
        transformer = TfidfTransformer()
        xTrain = transformer.fit_transform(xTrain)        
        xTest = transformer.fit_transform(xTest)

        appendDataTofile("Training dimension -> ",xTrain.shape)
        appendDataTofile("Testing dimension ->  ",xTest.shape)
        
        
        
                
        #MultinomialNB classification
        """appendDataTofile("MultiNB");
        yPred = classify(lambda:naive_bayes.MultinomialNB(),xTrain,xTest,yTrain,yTest)"""
        
        #Logistic Regression classification
        #penalty="l1",C=0.5,intercept_scaling=2
        """appendDataTofile("Log regression");
        yPred = classify(lambda:linear_model.LogisticRegression(),
                 xTrain,xTest,yTrain,yTest)"""
                                  
        """#SVM based classification
        appendDataTofile("SVM");
        #C=8.0,gamma=0.10,kernel='rbf',probability=True,shrinking=True
        yPred = classify(lambda:svm.SVC(),
                 xTrain,xTest,yTrain,yTest)"""
        
        #Grid search SVM
        """yPred =  gridSearchCVforSVM(xTrain,xTest,yTrain,yTest)"""
        
        yPred =  clusterify(lambda:KMeans(n_clusters=3),xTrain,xTest,yTrain,yTest)
         
        """yPred = classify(lambda:KNeighborsClassifier(),xTrain,xTest,yTrain,yTest)""" 
        """yPred = classify(lambda: linear_model.RidgeClassifierCV,xTrain,xTest,yTrain,yTest)"""
        
        outputFile = open("/Users/raghavan/Documents/workspace/datamining/data/raghavan/classificationresults.txt", 'w+')
        rows = len(yPred)
        #outputFile.write("ID\tSentiment\n");
        for i in range(0,rows):
            outputFile.write(str(yPred[i])+"\n")
        outputFile.close()
        
        
if __name__ == '__main__':
    if len(sys.argv) < 4:
        print 'Please provide the training file and test file'
        print 'python datamodeller.py <training-file-path> <test_file> <result_file>'
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    result_file = sys.argv[3]    
    if os.path.exists(result_file):
        os.remove(result_file);

    model = DataModeller(training_file, test_file,result_file)
    model.runAnalysis()
        
        
