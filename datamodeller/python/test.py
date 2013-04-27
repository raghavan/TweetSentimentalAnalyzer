import numpy as np
import scipy
import sys
from sklearn import linear_model,svm,naive_bayes,neighbors
from sklearn.metrics import confusion_matrix
from sklearn.metrics import precision_recall_fscore_support,classification_report
from sklearn import preprocessing as pp
from sklearn import cross_validation as cv
from sklearn.decomposition import SparsePCA
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.neighbors import KNeighborsClassifier


def classify(func,xTrain,xTest,yTrain,yTest):
        
        print "Training dimension -> ",xTrain.shape
        print "Testing dimension ->  ",xTest.shape
        
        clf = func()
        clf.fit(xTrain, yTrain);
        yPred = clf.predict(xTest); 
        resultLR =  clf.score(xTest,yTest);
        print "accuracy =" , resultLR.mean();             
        print "Confusion matrix",confusion_matrix(yTest, yPred);        
        precScores = precision_recall_fscore_support(yTest, yPred);
        
            
class test:
    
    def __init__(self, training_file, test_file):
        self.training_file = training_file
        self.test_file = test_file

    def runAnalysis(self):
        trainingData = np.loadtxt(open(self.training_file, 'rb'), delimiter = ',', skiprows = 0);
        testData = np.loadtxt(open(self.test_file,'rb'), delimiter = ',', skiprows = 0);
        
        xTrain =  trainingData[:, :trainingData.shape[1]-1]
        yTrain = trainingData[:,trainingData.shape[1]-1]
                  
        xTest = testData[:, :testData.shape[1] -1]
        yTest = testData[:, testData.shape[1]-1]


        classify(lambda:naive_bayes.MultinomialNB(),xTrain,xTest,yTrain,yTest)
    

    
if __name__ == '__main__':
    
    if len(sys.argv) < 2:
        print 'Please provide the training file and test file'
        print 'python datamodeller.py <training-file-path> <test_file>'
        sys.exit(1)
    
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    model = test(training_file, test_file)
    model.runAnalysis()    