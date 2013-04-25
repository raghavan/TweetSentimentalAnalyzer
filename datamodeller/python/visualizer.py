import numpy as np
import sys
import matplotlib
import pylab
from scipy.stats.stats import pearsonr
from sklearn import preprocessing

class visualizer:
    
    def __init__(self, training_file):        
        trainingData = np.loadtxt(open(training_file, 'rb'), delimiter = ',', skiprows = 0)
        self.xTrain =  trainingData[:, :trainingData.shape[1]-1]
        self.yTrain =  trainingData[:, trainingData.shape[1]-1]
        
    def driveAnalysis(self):
#        self.visualizeLabels()
#        self.visualizeFeatures()
        self.correlateFeatures()        
        
    def correlateFeatures(self):
        self.standardizedTrainingData = self.xTrain       
        labels = self.yTrain
        for i in range(1,10):
            feature = self.standardizedTrainingData[:,i]
            print pearsonr(feature, labels)
            self.visualizeFeatures(i)
        
        
                
    def visualizeFeatures(self,i):        
        labels = self.yTrain
        feature = self.standardizedTrainingData[:,i]
        matplotlib.pyplot.scatter(feature, labels)
        matplotlib.pyplot.xlabel('feature')
        matplotlib.pyplot.ylabel('labels')
        matplotlib.pyplot.title('Feature vs Label')
        matplotlib.pyplot.savefig('../visualizations/'+str(i)+'.png')
        
        
        
        
    def visualizeLabels(self):
        print self.trainingData.shape
        numberOfRecords = self.trainingData.shape[0]
        ratings = self.trainingData[:,self.trainingData.shape[1]-1]
        sortedRatings = np.sort(ratings)
        ratingsMap = {}
        for i in range(0, len(sortedRatings)):
            if sortedRatings[i] in ratingsMap:
                ratingsMap[sortedRatings[i]] += 1
            else:
                ratingsMap[sortedRatings[i]] = 1
        
        ratingsKeys = ratingsMap.keys()
        ratingLabels = np.ones(len(ratingsKeys))
        ratingValues = np.ones(len(ratingsKeys))
        
        for j in range(0, len(ratingsKeys)):
            ratingLabels[j] = ratingsKeys[j]
            ratingValues[j] = ratingsMap[ratingsKeys[j]]
        
        matplotlib.pyplot.scatter(ratingLabels, ratingValues)
        matplotlib.pyplot.title('ids vs ratings (Distribution of ratings)')
        matplotlib.pyplot.xlabel('ids')
        matplotlib.pyplot.ylabel('ratings')
        matplotlib.pyplot.savefig('../visualizations/ratings_distribution.png')
        
    def standardizeVector(self, inputData):
        """ removes mean and unit variance from """
        meanVector = np.mean(inputData, axis=0)
        stdVector = np.std(inputData, axis=0)
        
        if len(inputData.shape) == 1:
            row = len(inputData)
            col = 1
        else:
            row , col = inputData.shape
        regData = np.zeros((row, col))
        for i in range(0,row):
            regData[i] = np.divide(np.subtract(inputData[i], meanVector), stdVector)    
        
        return regData
    
if __name__ == "__main__":
    if len(sys.argv) < 2:
        print "Please provide the training input"
        print "python featureselector.py <input-to-training-file>"
        sys.exit(1)
        
    trainingFilePath = sys.argv[1]
    selector = visualizer(trainingFilePath)
    selector.driveAnalysis()
        
        