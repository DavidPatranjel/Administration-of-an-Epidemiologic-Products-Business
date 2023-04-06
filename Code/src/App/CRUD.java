package App;

import Models.Mask.Mask;
import Models.Sanitizer.Sanitizer;

public interface CRUD {
    void addMask(Mask newMask);
    void deleteMask(int index);
    void addSanitizer(Sanitizer newSanitizer);
    void deleteSanitizer(int index);
}
