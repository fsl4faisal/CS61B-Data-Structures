// A client that uses the synthesizer package to replicate a plucked guitar string sound
public class GuitarHero {
      public static void main(String[] args) {

          // create two guitar strings, for concert A and C
          String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
          synthesizer.GuitarString[] strings = new synthesizer.GuitarString[37];
          for (int i= 0; i < 37; i++) {
            double note = 440 * Math.pow(2, ((i - 24) / 12.0));
            strings[i] = new synthesizer.GuitarString(note);
          }
          

          while (true) {

              // check if the user has typed a key; if so, process it   
              if (StdDraw.hasNextKeyTyped()) {
                  char key = StdDraw.nextKeyTyped();
                  if (!(keyboard.indexOf(key) == -1)) {
                  int guitarStringIndex = keyboard.indexOf(key);
                  strings[guitarStringIndex].pluck(); 
                }
              }

              // compute the superposition of samples
              double sample = 0.0;
              for (int i= 0; i < 37; i++) {
                sample += strings[i].sample();
              }
              
  
              // play the sample on standard audio
              // note: this is just playing the double value YOUR GuitarString
              //       class is generating. 
              StdAudio.play(sample);
  
              // advance the simulation of each guitar string by one step   
              for (int i= 0; i < 37; i++) {
                strings[i].tic();
              }
          }
       }
  }

