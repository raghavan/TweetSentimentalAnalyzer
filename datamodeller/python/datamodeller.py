
import numpy
import scipy
import sys
from sklearn import linear_model
from scipy.io import arff
import pylab as pl
from sklearn.naive_bayes import MultinomialNB

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
                    
        #print xTrain;        
        #print xTest;
        #print yTrain;        
        #print yTest;
        
        #Logistic Regression classification
        logreg = linear_model.LogisticRegression();
        logreg.fit(xTrain,yTrain);
        logreg.predict(xTest);
        resultLR =  logreg.score(xTest,yTest);
        print "Log Reg mean accuracy =" , resultLR;
        
        #MultinomialNB classification
        mutlinb = MultinomialNB();
        mutlinb.fit(xTrain,yTrain);
        mutlinb.predict(xTest);
        resultMNB =  mutlinb.score(xTest,yTest);
        print "MultiNB mean accuracy =" , resultMNB;
        
if __name__ == '__main__':
    if len(sys.argv) < 2:
        print 'Please provide the training file, validation file and test file'
        print 'python datamodeller.py <training-file-path> '
        sys.exit(1)
    training_file = sys.argv[1]
    test_file = sys.argv[2]
    model = DataModeller(training_file, test_file)
    model.runAnalysis()
        
        
