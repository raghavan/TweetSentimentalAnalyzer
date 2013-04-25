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


def printPrecRecall(scores):
    print "Class Label        -1           0               1                2"
    print "Precision ",scores[0] 
    print "Recall    ",scores[1]
    print "F-Score   ",scores[2]
    print "Total Instances ",scores[3]    
    print "\n"
    
def transform(xTrain,yTrain,xTest):
    pca = SparsePCA(n_components=200,n_jobs=10);
    newXTrain =  pca.fit_transform(xTrain,yTrain)
    newXTest = pca.transform(xTest)
    return newXTrain,newXTest   

class DataModeller:
    
    def __init__(self, training_file, test_file):
        self.training_file = training_file
        self.test_file = test_file
        
    def runAnalysis(self):
        
        trainingData = np.loadtxt(open(self.training_file, 'rb'), delimiter = ',', skiprows = 0);
        testData = np.loadtxt(open(self.test_file,'rb'), delimiter = ',', skiprows = 0);
        
        #trainingData = np.genfromtxt(open(self.training_file,'rb'),delimiter=',');
        #testData = np.genfromtxt(open(self.testData,'rb'),delimiter=',');       
        xTrain =  trainingData[:, :trainingData.shape[1]-1]
        yTrain = trainingData[:,trainingData.shape[1]-1]
                  
        xTest = testData[:, :testData.shape[1] -1]
        yTest = testData[:, testData.shape[1]-1]
        
        #tf-idf transformation
        transformer = TfidfTransformer()
        xTrain = transformer.fit_transform(xTrain)        
        xTest = transformer.fit_transform(xTest)

        print "Training dimension -> ",xTrain.shape
        print "Testing dimension ->  ",xTest.shape
        
        #xTrain,xTest = transform(xTrain,yTrain,xTest)
        
        print "Reduced Training dimension -> ",xTrain.shape
        print "Reduced Testing dimension ->  ",xTest.shape
        
        #MultinomialNB classification
        print "MultiNB";
        mutlinb = naive_bayes.MultinomialNB();
        mutlinb.fit(xTrain,yTrain);
        yPred = mutlinb.predict(xTest);
        resultMNB =  mutlinb.score(xTest,yTest);        
        print "MNB mean accuracy =" , resultMNB;     
        print "Confusion matrix",confusion_matrix(yTest, yPred);        
        precScores = precision_recall_fscore_support(yTest, yPred);
        printPrecRecall(precScores)

        #Logistic Regression classification
        print "Log regression";
        logreg = linear_model.LogisticRegression(penalty="l1",C=0.5,intercept_scaling=2);
        logreg.fit(xTrain,yTrain);
        yPred = logreg.predict(xTest);                
        resultLR =  logreg.score(xTest,yTest);        
        print "Log Reg mean accuracy =" , resultLR.mean();             
        print "Confusion matrix",confusion_matrix(yTest, yPred);        
        precScores = precision_recall_fscore_support(yTest, yPred);
        printPrecRecall(precScores)        
                                
        outputFile = open("../files/classifiedtweets.csv", 'w+')
        rows = len(yPred)
        outputFile.write("Predicted,Actual\n");
        for i in range(0,rows):
            outputFile.write(str(yPred[i]) +","+ str(yTest[i])+"\n")
        outputFile.close()
               
        
     
        
        
        #SVM based classification
        print "SVM";
        svmclf = svm.SVC(C=8.0,gamma=0.10,kernel='rbf',probability=True,shrinking=True);
        svmclf.fit(xTrain,yTrain);
        yPred = svmclf.predict(xTest);
        resultSVM =  svmclf.score(xTest,yTest);        
        print "SVM mean accuracy =" , resultSVM;     
        print "Confusion matrix",confusion_matrix(yTest, yPred);   
        precScores = precision_recall_fscore_support(yTest, yPred);
        printPrecRecall(precScores)      
        
if __name__ == '__main__':
    if len(sys.argv) < 2:
        print 'Please provide the training file and test file'
        print 'python datamodeller.py <training-file-path> <test_file>'
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    model = DataModeller(training_file, test_file)
    model.runAnalysis()
        
        
