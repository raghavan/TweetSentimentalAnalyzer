
import numpy
import scipy
import sys
from sklearn import linear_model,svm,naive_bayes
from scipy.io import arff
import pylab as pl
import pickle
from sklearn.metrics import confusion_matrix
from sklearn.metrics import precision_recall_fscore_support

class DataModeller:
    
    def __init__(self, training_file, test_file):
        self.training_file = training_file
        self.test_file = test_file
        
    def runAnalysis(self):
        
        train_data, train_meta = arff.loadarff(self.training_file);
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
        yTest = test_data['class'];
        
        #Logistic Regression classification
        logreg = linear_model.LogisticRegression();
        logreg.fit(xTrain,yTrain);
        yPred = logreg.predict(xTest);
        print yPred;
        print yTest;            
        resultLR =  logreg.score(xTest,yTest);   
        print "Log regression";             
        print "Confusion matrix",confusion_matrix(yTest, yPred);        
        print "Prec,recall,fscore ",precision_recall_fscore_support(yTest, yPred,average='weighted');
        print "Log Reg mean accuracy =" , resultLR;
        
                
            
        outputFile = open("../files/classifiedtweets.csv", 'w+')
        rows = len(yPred)
        outputFile.write("Predicted,Actual\n");
        for i in range(0,rows):
            outputFile.write(str(yPred[i]) +","+ str(yTest[i])+"\n")
        outputFile.close()
        
        #MultinomialNB classification
        mutlinb = naive_bayes.MultinomialNB();
        mutlinb.fit(xTrain,yTrain);
        yPred = mutlinb.predict(xTest);
        resultMNB =  mutlinb.score(xTest,yTest);
        print "MultiNB";     
        print "Confusion matrix",confusion_matrix(yTest, yPred);        
        print "Prec,recall,fscore ",precision_recall_fscore_support(yTest, yPred, average='macro') 
        print "MNB mean accuracy =" , resultMNB;
        
        #SVM based classification
        #svmclf = svm.SVC();
        #svmclf.fit(xTrain,yTrain);
        #svmclf.predict(xTest);
        #resultSVM =  svmclf.score(xTest,yTest);
        #print "SVM mean accuracy =" , resultSVM;
        
if __name__ == '__main__':
    if len(sys.argv) < 2:
        print 'Please provide the training file, validation file and test file'
        print 'python datamodeller.py <training-file-path> '
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    model = DataModeller(training_file, test_file)
    model.runAnalysis()
        
        
