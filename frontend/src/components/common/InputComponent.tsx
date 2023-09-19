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
    <div className="pl-8 py-4 relative">
      <input
        className="w-48 h-8 rounded-sm pl-10 text-lg"
        type={type}
        value={value}
        placeholder={placeholder}
        onChange={onChange}
        onKeyDown={onKeyDown}
      />
      <MagnifyingGlassIcon className="w-8 h-8 absolute top-4 left-9 text-gray-900" />
    </div>
  );
}
