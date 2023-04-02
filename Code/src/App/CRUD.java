package App;

import Models.Mask.Mask;
import Models.Sanitizer.Sanitizer;

public interface CRUD {
    void addMask(Mask newMask);
    void addSanitizer(Sanitizer newSanitizer);
}
