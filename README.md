# WELCOME YOU TUTORIAL LEARNING ANDROID [![Build Status](https://travis-ci.org/nomensa/jquery.hide-show.svg)](https://travis-ci.org/nomensa/jquery.hide-show.svg?branch=master)

> **Nickname:** Lorence

> **Hobby:** Soccer and Chess

> **Major:** Programmer

> **Address:** VietNam

> **Schedule** #[Schedule](#schedule)

## Task

> **Version: ** ver25
>> **Load DatePickerDialog and TimePickerDialog**

private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    @OnClick(R.id.btnReminder)
    public void openDatePicker() {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mTimePickerDialog.show();
            }

        };

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateDisplay(myCalendar);
            }
        };

        mDatePickerDialog = new DatePickerDialog(mActivity, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMinDate(new Date().getTime()-(new Date().getTime()%(24*60*60*1000)));
        
        mTimePickerDialog = new TimePickerDialog(getActivity(), time,
                myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
        mDatePickerDialog.show();
    }
    
    => Disable past dates: mDatePickerDialog.getDatePicker().setMinDate(new Date().getTime()-(new Date().getTime()%(24*60*60*1000)));

>> **Validate date/time when picked from user**

