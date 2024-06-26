import sys
import sklearn as sk
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.model_selection import cross_val_score
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.svm import SVC
from sklearn.model_selection import GridSearchCV

try:
    movie_data = pd.read_csv('movies!.csv')
except pd.errors.ParserError:
    print('Error parsing CSV file. skipping problematic lines.')
    movie_data = pd.read_csv('movies!.csv', error_bad_lines=False)

catagory = sys.argv[1]
genre = sys.argv[2]
length = sys.argv[3]
#person = 
#person2 = (sys.argv[5])
#director = (sys.argv[6])
movie_data.dropna(inplace=True)
movie_data.drop_duplicates(inplace=True)

X = movie_data['Overview']
y = movie_data['Genre']

vectorizer = TfidfVectorizer()
X_transformed = vectorizer.fit_transform(X)

classifier = MultinomialNB()
classifier.fit(X_transformed, y)

scores = cross_val_score(classifier, X_transformed, y, cv=5)
print('Mean accuracy:', scores.mean())

movie_data['Overview'] = movie_data['Overview'].fillna('')

vectorizer = TfidfVectorizer(stop_words='english')
X = vectorizer.fit_transform(movie_data['Overview'])
print('Shape of TF-IDF matrix:', X.shape)

param_grid = {
    'C': [0.1, 1, 10, 100],       # Regularization parameter
    'gamma': [0.001, 0.01, 0.1],  # Kernel coefficient for 'rbf'
    'kernel': ['rbf', 'linear']   # Kernel type
}

# Initialize SVM classifier
svm_classifier = SVC()

# Perform GridSearchCV
grid_search = GridSearchCV(estimator=svm_classifier, param_grid=param_grid, cv=5)
grid_search.fit(X_transformed, y)

# Print the best parameters and best score
print("Best parameters found:", grid_search.best_params_)
print("Best cross-validation score:", grid_search.best_score_)