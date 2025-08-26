package com.example.impl.presentation.ui.editor.screenmodel

import com.example.utils.extensions.duration
import com.example.utils.functional.Constants.Date.HOURS_IN_DAY
import com.example.utils.functional.Constants.Date.MILLIS_IN_HOUR
import com.example.utils.functional.TimeRange
import com.example.utils.validation.ValidateError
import com.example.utils.validation.ValidateResult
import com.example.utils.validation.Validator
import kotlinx.parcelize.Parcelize

interface TimeRangeValidator: Validator<TimeRange,TimeRangeError>  {
 class Base: TimeRangeValidator{
  override fun validate(data : TimeRange) : ValidateResult<TimeRangeError>{
   return if(duration(data)==0L || duration(data) >MILLIS_IN_HOUR*HOURS_IN_DAY){
    ValidateResult(isValid = false, validError = TimeRangeError.DurationError)
   }else{
    ValidateResult(isValid = true, validError = null)
   }
  }
 }

}
@Parcelize
sealed class  TimeRangeError : ValidateError{
 object DurationError : TimeRangeError()
}