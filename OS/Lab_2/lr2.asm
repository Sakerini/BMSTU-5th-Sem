.386p

include my_macro.inc

; ====== структура дескриптора сегмента в GDT ======
seg_descr struc    
    lim 	dw 0	; Граница (биты 0..15)  - размер сегмента в байтах
    base_l 	dw 0	; Младшие 16 битов адресной базы - базовый адрес задаётся в виртуальном адресном пространстве
    base_m 	db 0	; Следующие 8 битов адресной базы.
    attr_1	db 0	; атрибуты
    attr_2	db 0	; атрибуты
    base_h 	db 0	; Последние 8 битов адресной базы.
seg_descr ends

; ====== дескриптор прерываний в IDT ======
int_descr struc 
    offs_l 	dw 0  ; Младшие 16 битов адреса, куда происходит переход в случае возникновения прерывания.
    sel		dw 0  ; Селектор сегмента с кодом прерывания/Переключатель сегмента ядра
    cntr    db 0  ; Счётчик, не используется в данной программе. 
    attr	db 0  ; Атрибуты (указывает, какой будет тип)
    offs_h 	dw 0  ; Старшие 16 битов адреса, куда происходит переход.
int_descr ends

; сегмент стека (так как есть call)
stack_seg segment  para stack 'STACK'
    stack_start	db	100h dup(?)
    stack_size = $-stack_start
stack_seg 	ENDS

; сегмент данных 
data_seg segment para 'DATA'

	;====== глобальная таблица дескрипторов сегментов (GDT) ======

    ; обязательный нулевой дескриптор 
    gdt_null  seg_descr <>
    
    ; 16-битный сегмент кода - для реального режима
    gdt_CS_16bit seg_descr <rm_code_size-1, 0, 0, 10011000b, 00000000b, 0>

    ; 16-битный сегмент данных, размером 4гб - для реального режима 
    ; 00 в аттр_1 - DPL = 0
    gdt_DS_32bit seg_descr <0FFFFh, 0, 0, 10010010b, 11001111b, 0>  ; (атр_2 D=0) (гранулярность в атр_2-память в стр.)

    ; 32-битный сегмент кода - для защищенного режима
    gdt_CS_32bit seg_descr <pm_code_size-1, 0, 0, 10011000b, 01000000b, 0>    

    ; 32-битный сегмент данных - для защищенного режима
    gdt_DS_32bit seg_descr <data_size-1, 0, 0, 10010010b, 01000000b, 0>  ; 32разрядный!

    ; 32-битный сегмент стека - для защищенного режима 
    gdt_SS_32bit seg_descr <stack_size-1, 0, 0, 10010110b, 01000000b, 0>
        
    ; 32-битный сегмент видеопамяти ( видеобуфера )
    gdt_VB_32bit seg_descr <3999, 8000h, 0Bh, 10010010b, 01000000b, 0>

    gdt_size = $-gdt_null ; размер таблицы  GDT 
    gdtr	df 0	      ; тут будет храниться базовый линейный адрес и размер таблицы GDT

    ; смещения дескрипторов сегментов в таблице
    ; GDT - размер дескриптора 8 байт
	; в селекторе первые 3 бита == 0, потому что работает с глобальной табл сегментов (TI = 0), RPL = 0
	; (когда кратно 8 , это 3 бита == 0)
    ; СЕЛЕКТОРЫ
    sel_CS_16bit    equ    8   
    sel_DS_16bit    equ   16   
    sel_CS_32bit    equ   24
    sel_DS_32bit    equ   32
    sel_SS_32bit    equ   40
    sel_videobuffer equ   48
    

	; ====== таблица дескрипторов прерываний (IDT) ======
    
    IDT	label byte

    ; первые 32 дескриптора - исключения, в программе не используются
	; + заглушка для 13
	; trap gate 32р
	trap_f int_descr 12 dup (<0, sel_CS_32bit, 0, 10001111b, 0>) 
	trap_13 int_descr <0, sel_CS_32bit, 0, 10001111b, 0>  ; general protection
	trap_s int_descr 19 dup (<0, sel_CS_32bit, 0, 10001111b, 0>) 

    ; дескриптор прерывания от таймера
    int08 int_descr <0, sel_CS_32bit, 0, 10001110b, 0> ; аппаратное прерывание (interrupt gate 32р)

    ; дескриптор прерывания от клавиатуры
    int09 int_descr	<0, sel_CS_32bit, 0, 10001110b, 0> 

    idt_size = $-IDT ; размер таблицы IDT

    idtr df 0                       ; будет хранить базовый линейный адрес (4 байта) таблицы IDT и ее размер (2 байта)
	; содержимое регистра IDTR в реальном режиме
    idtr_backup dw	3FFh, 0, 0      ; чтобы запомнить предыдущее значение и восстановить его при переходе обратно в реальный режим, 3ff - первый кб (размер), нулевой адрес

    mask_master	db 0		; маска прерывания ведущего контроллера
    mask_slave	db 0		; маска прерывания ведомого контроллера
        

	; Номер скан кода (с клавиатры) символа ASCII == номеру соответствующего элемента в таблице:
	ascii	db 0, 0, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 45, 61, 0, 0
			db 81, 87, 69, 82, 84, 89, 85, 73, 79, 80, 91, 93, 0, 0, 65, 83
			db 68, 70, 71, 72, 74, 75, 76, 59, 39, 96, 0, 92, 90, 88, 67
			db 86, 66, 78, 77, 44, 46, 47

    flag_enter_pr	    db 0				 
    cnt_time	        dd 0		    ; счетчик тиков таймера  (4 байта)
    syml_pos     dd 2 * (80 * 10)   ; Позиция выводимого символа

    ; выводимые сообщения
    msg_in_rm   db 27, ';Now in Real Mode. ', 27, '[0m$'
    msg_move_pm db 27, ';To enter Protected Mode press any key!', 27, '[0m$'
    msg_out_pm  db 27, ';Now in Real Mode again! ', 27, '[0m$'

    data_size = $-gdt_null ; размер сегмента данных
data_seg ends


PM_code_seg segment para public 'CODE' use32

    assume cs:PM_code_seg, ds:data_seg, ss:stack_seg

    pm_start:
			; в регистры сегмента загружаем селекторы
            mov	ax, sel_DS_32bit 
            mov	ds, ax
            mov	ax, sel_videobuffer
            mov	es, ax
            mov	ax, sel_SS_32bit
            mov	ss, ax
            mov	eax, stack_size
            mov	esp, eax

        sti ; разрешить прерывания, запрещенные в реальном режиме
            ; выход из цикла - по нажатию Enter

        ; вывод строк - сохранение символов в видеопамять
        mem_str

        ; подсчет и вывод объема доступной памяти
        call count_memory

        ; Возвращение в реальный режим происходит по нажатию
        ; клавиши 'enter' - это будет обработано в коде обработчика прервания 
        ; чтобы программа не завершалась до этого момента, нужен бескончный цикл
    proccess:
        test flag_enter_pr, 1
        jz	proccess

        ; запрещаем прерывания
        ; немаскируемые уже запрещены
        cli ; сброс флага прерывания IF = 0
        far_jump return_rm, sel_CS_16bit
		
		except_1 proc
			iret
		except_1 endp
	
		except_13 proc
			pop eax
			iret
		except_13 endp
    
		; обработчик системного таймера
        new_int08 proc uses eax 

                ; получили текущее количество тиков
                mov  eax, cnt_time
				push eax
                ; вывели время
					mov edi, 80 * 2
					xor eax, eax
					test cnt_time, 05
					jz X
					test cnt_time, 09
					jnz skip
					
					mov al, ' '
					jmp pr
				X:
					mov al, 'X'
				pr:
					mov ah, 7
					stosw		
						
				skip:	
					pop eax
                ; увеличили текущее количество счетчиков
					inc eax

                ; сохранили 
                mov cnt_time, eax

                ; отправили EOI ведущему контроллеру прерываний
                mov	al, 20h 
                out	20h, al
                
                iretd
        new_int08 endp

        new_int09 proc uses eax ebx edx 
            in	al, 60h      ; Получить скан-код нажатой клавиши из порта клавиатуры

            cmp	al, 1Ch 	        ; Сравниваем с кодом enter
            jne	print_value         ; Если не enter - выведем, то что ввели
            or flag_enter_pr, 1     ; Если enter - устанавливаем флаг, возврата в реальный режим
            jmp allow_handle_keyboard
            
            print_value:
                cmp al, 80h  ; Сравним какой скан-код пришел: нажатой клавиши или отжатой?
                ja allow_handle_keyboard 	 ; Если отжатой, то ничего не выводим
                                
                xor ah, ah	 
                
                ; mov ebx, 2 * 80 * 4 ; вывод скан-кода
                ; call print_eax
                
                xor ebx, ebx
                mov bx, ax

                mov dl, ascii[ebx]  
                mov ebx, syml_pos
                mov es:[ebx], dl

                add ebx, 2
                mov syml_pos, ebx

            allow_handle_keyboard: 
                in	al, 61h ; сообщаем контроллеру о приёме скан кода:
                or	al, 80h ; установкой старшего бита 
                out	61h, al ; содержимого порта B
                and al, 7Fh ; и последующим его сбросом
                out	61h, al

                mov	al, 20h ; End of Interrupt ведущему контроллеру прерываний (закончили обработку прерывания)
                out	20h, al

                iretd
        new_int09 endp

        count_memory proc uses ds eax ebx 
            mov ax, sel_DS_16bit
            mov ds, ax
			
			; подсчет памяти
			;первый мегабайт пропустить; начиная со второго мегабайта сохранить байт или слово памяти, записать в этот 
			;байт или слово сигнатуру, прочитать сигнатуру и сравнить с сигнатурой в программе, если сигнатуры совпали, то это – память. 
            
            mov ebx, 100001h ; пропустить первый мегабайт, чтобы не изменить bios! (попытаемся изменить данные bios, к. readonly память)
            mov dl, 10101110b  ; то, что будем печатать в память (сигнатура)
            
            mov	ecx, 0FFEFFFFEh; количество оставшейся памяти (до превышения лимита в 4ГБ) - защита от переполнения

            iterate_through_memory:
                mov dh, ds:[ebx] ; сохраняем байт памяти

                mov ds:[ebx], dl        ; пишем в память
                cmp ds:[ebx], dl        ; проверяем - если записано то, что мы пытались записать
                                                ; то это доступная память
                jnz print_memory_counter        ; иначе мы дошли до конца памяти - надо вывести
            
                mov	ds:[ebx], dh ; восстановить байт памяти
                inc ebx          ; если удалось записать - увеличиваем счетчик памяти 
            loop iterate_through_memory

            print_memory_counter:
                mov eax, ebx ; переводим в Mb
                xor edx, edx

                mov ebx, 100000h ; 1 Mb
                div ebx

                mov ebx, 2 * 10
                call print_eax

                mov ebx, 2 * 20
                mov al, 'M'
                mov es:[ebx], al

                mov ebx, 2 * 20 + 2
                mov al, 'b'
                mov es:[ebx], al
            ret
        count_memory endp


        ; вывод значения eax в видеобуффер
        ; в ebx позиция вывода на экран 
        print_eax proc uses ecx ebx edx     
                add ebx, 10h 
                mov ecx, 8   
            
            print_symbol: 
                mov dl, al
                and dl, 0Fh      
                
                cmp dl, 10
                jl add_zero_sym
                add dl, 'A' - '0' - 10 

            add_zero_sym:
                add dl, '0' ; преобразуем число в цифру               
                mov es:[ebx], dl ; записать в видеобуффер
                ror eax, 4       ; циклически сдвинуть вправо
                sub ebx, 2       ; печатаем след символ
                loop print_symbol
            ret
        print_eax endp

    pm_code_size = $-pm_start 	
PM_code_seg ends

RM_code_seg segment para public 'CODE' use16
    assume cs:RM_code_seg, ds:data_seg, ss: stack_seg

    start:
        mov ax, data_seg
        mov ds, ax

        mov ax, PM_code_seg
        mov es, ax

        print_str msg_in_rm  
        print_str msg_move_pm

        wait_key
        clear_screen 

        xor	eax, eax
		
		; загружаем адреса сегментов
        mov	ax, RM_code_seg 
        load_gdt gdt_CS_16bit ; в дескр сегмента

        mov ax, PM_code_seg
        load_gdt gdt_CS_32bit

        mov ax, data_seg
        load_gdt gdt_DS_32bit

        mov ax, stack_seg
        load_gdt gdt_SS_32bit

        mov ax, data_seg  
        shl eax, 4   ;лин баз адрес сегмента data_seg (* 16)
        add	eax, offset gdt_null ; в eax - лин баз адрес начала gdt
        init_gdtr eax
		
			lea eax, es:except_1
            load_idt trap_f
			
			lea eax, es:except_1
            load_idt trap_s
			
			lea eax, es:except_13
            load_idt trap_13;

			; загружаем дескриптор прерывания, нужно только смещение, тк селектор кода уже указан
            lea eax, es:new_int08   ; в eax смещение 8 обработчика
            load_idt int08 ; прерывание таймера

            lea eax, es:new_int09
            load_idt int09; прерывание клавиатуры
            
            mov ax, data_seg
            shl eax, 4
            add	eax, offset IDT; в eax полный линейный адрес IDT
            init_idtr eax
			
			; для возврата в защищенный:
			;сохраним маски прерываний контроллеров
            in	al, 21h						; получить набор масок (флагов) mask_master 21h - номер шины, in на неё даст нам набор масок (флагов)
            mov	mask_master, al			    ; сохраняем в переменной mask_master (понадобится для возвращения в RM)
            in	al, 0A1h					; аналогично ведомого,  in даёт набор масок для ведомого
            mov	mask_slave, al

			; перепрограммируем пик (контроллер)
			; вектор прерывания = базовый вектор прерывания + № IRQ
			; irq0 - системный таймер, 8 + 0 = 8ое исключение => system fault, паника системы
			; необходимо перепрограмироваить пик на новый новый базовый вектор 32
			set_interrupt_base 32 

            ; Запретим все прерывания в ведущем контроллере, кроме IRQ0 (таймер) и IRQ1(клавиатура)
            ; установление новых масок
            mov	al, 0FCh
            out	21h, al
            ; запретим все прерывания в ведомом контроллере
            mov	al, 0FFh
            out	0A1h, al
			
			; загрузим IDT
            lidt fword ptr idtr                                    ; ПОСМОТРЕТЬ lidt и открытие а20 
			
			; открытие линии A20  
			; включает механизм циклического оборачивания адреса => можно адресоваться к расширенной памяти (за пределы 1мб)
            in	al, 92h						
            or	al, 2						
            out	92h, al						

            cli         ; отключить маскируемые прерывания
            in	al, 70h ; и немаскируемые прерывания
            or	al, 80h
            out	70h, al

			; ПЕРЕХОД В ЗАЩИЩЕННЫЙ РЕЖИМ
            mov	eax, cr0
            or eax, 1     ; перейти в непосредственно защищенный режим (PE делаем == 1)
            mov	cr0, eax

            db	66h ; far jmp sel_CS_32bit:pm_start ?
            far_jump pm_start, sel_CS_32bit


    return_rm:
       
			; ВОЗВРАЩАЕМСЯ В РЕАЛЬНЫЙ РЕЖИМ 
            mov	eax, cr0
            and	al, 0FEh 				; сбрасываем флаг защищенного режима
            mov	cr0, eax

            ; этот дальний переход необходим для модификации теневого регистра cs
            db	0EAh	; far jmp RM_code_seg:$+4
            dw	$+4	    ; *выполнить следующую после dw	RM_code_seg команду
            dw	RM_code_seg

            mov	eax, data_seg	; загружаем в сегментные регистры сегменты
            mov	ds, ax          
            mov eax, PM_code_seg
            mov	es, ax
            mov	ax, stack_seg   ; сдеалем адресуемым стек
            mov	ss, ax
            mov	ax, stack_size
            mov	sp, ax

			;перепрограммируем контроллер
            set_interrupt_base 8 ; теперь базовый вектор прерывания снова = 8 - смещение, 
			                     ; по которому вызываются стандартные обработчики прерываний в реалмоде

            mov	al, mask_master ; восстанавить маски контроллеров прерываний
            out	21h, al
            mov	al, mask_slave
            out	0A1h, al

            ; загружаем таблицу дескриптров прерываний реального режима
            lidt	fword ptr idtr_backup

            in	al, 70h ; разрешить немаскируемые прерывания
            and	al, 7FH
            out	70h, al
            sti     ; и маскируемые

        clear_screen
        print_str msg_out_pm
        
        mov	ax, 4C00h
        int	21h

    rm_code_size = $-start 	; длина сегмента RM_code_seg
RM_code_seg	ends
end start