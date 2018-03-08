/*******************************************************************************
 * Copyright (c) 2010 Haifeng Li
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package smile.classification;

/**
 * Soft classifiers calculate a posteriori probabilities besides the class
 * label of an instance.
 *
 * @param <T> the type of input object
 *
 * @author Haifeng Li
 */
public abstract class SoftClassifier<T> implements Classifier<T> {
    
    /** Interrupt*/
    private final TrainingInterrupt interrupt;
    
    /**
     * Creates a new instance
     * @param interrupt
     */
    public SoftClassifier(TrainingInterrupt interrupt) {
        this.interrupt = interrupt;
    }
    
    /**
     * Predicts the class label of an instance and also calculate a posteriori
     * probabilities. Classifiers may NOT support this method since not all
     * classification algorithms are able to calculate such a posteriori
     * probabilities.
     *
     * @param x the instance to be classified.
     * @param posteriori the array to store a posteriori probabilities on output.
     * @return the predicted class label
     */
    public abstract int predict(T x, double[] posteriori);
    
    /**
     * Checks for interrupts
     * @throws InterruptedException 
     */
    protected void interrupt() throws TrainingInterruptedException {
        if (this.interrupt != null && this.interrupt.interrupt) {
            throw new TrainingInterruptedException();
        }
    }

    /**
     * Returns the interrupt flag
     * @return
     */
    TrainingInterrupt getInterrupt() {
        return this.interrupt;
    }

}
