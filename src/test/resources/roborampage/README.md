# Robo Rampage regression net

`legacy-behavior.txt` pins the observable rules recovered from the October 2015
source before its implementation is replaced. A golden diff is a semantic change
and must be explained. Regenerate deliberately with:

```sh
UPDATE_GOLDEN=1 ./gradlew test --tests '*RoboRampageLegacyBehaviorTest'
```
