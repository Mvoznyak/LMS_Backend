package courses.model

import common.marhallers.JsonSupport
import courses.model.CourseProtocol._

trait CoursesFormat extends JsonSupport {

  implicit val courseFormat = jsonFormat3(Course)
  implicit val courseMaterialFormat = jsonFormat5(CourseMaterial.apply)
  implicit val homeworkFormat = jsonFormat6(Homework.apply)
  implicit val courseDescriptionFormat = jsonFormat7(CourseDescription.apply)
  implicit val addMaterialRequestFormat = jsonFormat2(AddMaterialRequest)
  implicit val addHomeworkRequestFormat = jsonFormat4(AddHomeworkRequest)
  implicit val submitHomeworkRequestFormat = jsonFormat1(SubmitHomeworkRequest)
}
