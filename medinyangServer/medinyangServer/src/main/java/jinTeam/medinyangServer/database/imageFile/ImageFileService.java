package jinTeam.medinyangServer.database.imageFile;

import jinTeam.medinyangServer.common.exceptions.ResourceNotFoundException;
import jinTeam.medinyangServer.database.user.User;
import jinTeam.medinyangServer.database.user.UserService;
import jinTeam.medinyangServer.common.enums.MedicalType;
import jinTeam.medinyangServer.common.exceptions.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository repository;

}
