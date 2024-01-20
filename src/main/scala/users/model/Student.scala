package users.model

import java.util.UUID

case class Student(id: UUID,
                   groupId: UUID,
                   admissionYear: Int,
                   degree: String,
                   form: String,
                   basis: String)
