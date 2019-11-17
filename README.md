# Contacts
This Contacts application is build using the Android Jetpack architecture components, following are the components used.

1. Navigation Component
   The reason to use this component is, it gave a clean abstraction when navigating between fragment, and alleviated the
   codes to do the fragment transaction and maintain back-stack.

2. Retrofit
   Tons of reason to choose this component, but to brief. It takes care of all the boilerplate code to do the
   asynchronous network operation, serialization , de-serialization and etc.

3. LiveData
   The reason is obvious, didn't want to create a lot's of callback and stuck in callback hell. LiveData made the
   observation to the dataset painless, and it's lifecycle aware.

4. Room
   Again lots of reason to choose this, couple of them are, this goes well with LiveData, we can harness the power of
   SQL, and table creation from the class structure.



