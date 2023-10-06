import { MagnifyingGlassIcon } from '@heroicons/react/24/outline';
import React from 'react';

interface IInputProps {
  type: 'text' | 'password' | 'email' | 'entercode' | 'nickname';
  placeholder?: string;
  value?: string;
  // eslint-disable-next-line no-unused-vars
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  // eslint-disable-next-line no-unused-vars
  onKeyDown?: (event: React.KeyboardEvent<HTMLInputElement>) => void;
}

export default function InputComponent({
  type,
  placeholder = '입력해주세요',
  value,
  onChange,
  onKeyDown,
}: IInputProps) {
  return (
    <div className="relative py-4 pl-8">
      <input
        className="w-48 h-12 text-lg text-black rounded-md pl-11"
        type={type}
        value={value}
        placeholder={placeholder}
        onChange={onChange}
        onKeyDown={onKeyDown}
      />
      <MagnifyingGlassIcon className="absolute w-8 h-8 text-gray-900 top-6 left-9" />
    </div>
  );
}
