import numpy as np
import scipy
import sys
from sklearn import linear_model,svm,naive_bayes,neighbors
from scipy.io import arff
import pylab as pl
import pickle
from sklearn.metrics import confusion_matrix
from sklearn.metrics import precision_recall_fscore_support,classification_report
from sklearn import preprocessing as pp
from sklearn import cross_validation as cv
from sklearn.ensemble import ExtraTreesClassifier
import random



class DataModeller:
    
    def __init__(self, training_file):
        self.training_file = training_file
        
    def runAnalysis(self):
        
        trainingData = np.loadtxt(open(self.training_file, 'rb'), delimiter = ',', skiprows = 0);
        
               
        xTrain =  trainingData[:, :trainingData.shape[1]-1]
        yTrain = trainingData[:,trainingData.shape[1]-1]          
        
        """train_data, train_meta = arff.loadarff(self.training_file);
        test_data, test_meta = arff.loadarff(self.test_file);
        
        trainMetaSize = len(train_meta.names())-1;
        testMetaSize = len(test_meta.names())-1;
        xTrain = numpy.ndarray(shape=(train_data.shape[0],trainMetaSize));
        xTest = numpy.ndarray(shape=(test_data.shape[0],testMetaSize));       
        
        for i in range(trainMetaSize):
            if train_meta.names()[i] != 'class': 
                xTrain[:, i] = train_data[train_meta.names()[i]];
            
        for i in range(testMetaSize):
            if train_meta.names()[i] != 'class': 
                xTest[:, i] = test_data[test_meta.names()[i]];
                
        #yTrain = numpy.ndarray(shape=train_data.shape[0],1);
        yTrain = train_data['class'];
        yTest = test_data['class'];"""

        

        #Logistic Regression classification
        logreg = linear_model.LogisticRegression(penalty="l1",C=0.5,intercept_scaling=2)
        logreg.fit(xTrain,yTrain)
        scores = cv.cross_val_score(logreg,xTrain,yTrain)                         
        print "Log regression"
        print "Log Reg mean accuracy =" , scores.mean()                                
               
        
        #MultinomialNB classification
        mutlinb = naive_bayes.MultinomialNB();
        mutlinb.fit(xTrain,yTrain);
        scores = cv.cross_val_score(mutlinb,xTrain,yTrain)                         
        print "MNB"
        print "MNB mean accuracy =" , scores.mean()                                
        
        """ #SVM based classification
        svmclf = svm.SVC(C=12.0,kernel='rbf',probability=True,shrinking=True);
        svmclf.fit(xTrain,yTrain);
        scores = cv.cross_val_score(svmclf,xTrain,yTrain)                         
        print "Log regression"
        print "Log Reg mean accuracy =" , scores.mean()"""                      
        
if __name__ == '__main__':
    if len(sys.argv) < 1:
        print 'Please provide the training file and test file'
        print 'python datamodeller.py <training-file-path> <test_file>'
        sys.exit(1)
    training_file = sys.argv[1]
    model = DataModeller(training_file)
    model.runAnalysis()
        
        
